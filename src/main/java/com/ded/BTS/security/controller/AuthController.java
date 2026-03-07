package com.ded.BTS.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ded.BTS.enums.RoleNames;
import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.ApproveUserDetailRequest;
import com.ded.BTS.model.Role;
import com.ded.BTS.model.User;
import com.ded.BTS.model.UserRole;
import com.ded.BTS.model.UserSummary;
import com.ded.BTS.repository.RoleRepo;
import com.ded.BTS.repository.UserRepo;
import com.ded.BTS.security.model.JwtResponse;
import com.ded.BTS.security.model.LoginRequest;
import com.ded.BTS.security.model.RegisterRequest;
import com.ded.BTS.security.service.JwtService;
import com.ded.BTS.service.UserService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {


	@Value("${jwt.token.expiration}")
	private  long EXPIRATION; // 1hr

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserService userService;
	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userService=userService;
	}

	@Hidden
	@PostMapping(value = "/login", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> login(
	        @RequestParam String username,
	        @RequestParam String password,
	        @RequestParam(required = false) String grant_type
	) {

		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		String token = jwtService.generateToken(authentication);

		return ResponseEntity.ok(new JwtResponse(token,"Bearer",EXPIRATION/1000));
	}


	@Tag(name = "User Registration Operations")
	@Operation(summary = "Register an user")
	@PostMapping("/register-user")
	public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
		String res= userService.createUser(request);
		if(res!=null) {
	    return ResponseEntity.ok("User created successfully");
		}
		else {
		    return ResponseEntity.status(204).body("User created successfully \nAwaiting Administrator verification");

		}
	}
	
	
	@Tag(name = "User Role Assigning Operations")
	@Operation(summary = "Assign a role to an user")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/users/{username}/assign-roles")
	public ResponseEntity<?> assignRole(@PathVariable String username, @RequestBody List<String> roleName) {
		userService.assignRoleToUser(username, roleName);
		return ResponseEntity.ok("Role assigned successfully");
	}

	@Tag(name = "User Role Assigning Operations")
	@Operation(summary = "Get All Pending Auth user")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/users/pending-auth")
	public ResponseEntity<?> getPendingAuthorizationUsers(){
	 return	ResponseEntity.ok().body(userService.getPendingAuthorizationUsers());
	}
	@Tag(name = "User Role Assigning Operations")
	@Operation(summary = "Authorize an user")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/users/pending-auth")
	public ResponseEntity<?> AuthorizeUser(@RequestBody ApproveUserDetailRequest approveUserDetailRequest){
		 return	ResponseEntity.ok().body(userService.authorizeUser(approveUserDetailRequest));
		}

}
