package com.ded.BTS.security;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.Role;
import com.ded.BTS.model.User;
import com.ded.BTS.model.UserRole;
import com.ded.BTS.repository.RoleRepo;
import com.ded.BTS.repository.UserRepo;
import com.ded.BTS.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {



	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserService userService;
	public AuthController(AuthenticationManager authenticationManager, JwtService jwtService,UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userService=userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password_hash()));

		String token = jwtService.generateToken(authentication);

		return ResponseEntity.ok(new JwtResponse(token, "Token Creation Success"));
	}


	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/users")
	public ResponseEntity<?> createUser(@RequestBody RegisterRequest request) {
		userService.createUser(request);
	    return ResponseEntity.ok("User created successfully");
	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/users/{username}/roles")
	public ResponseEntity<?> assignRole(
	        @PathVariable String username,
	        @RequestBody String roleName) {
		userService.assignRoleToUser(username, roleName);
	    return ResponseEntity.ok("Role assigned successfully");
	}



}
