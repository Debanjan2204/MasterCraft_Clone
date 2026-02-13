package com.ded.BTS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.Role;
import com.ded.BTS.model.User;
import com.ded.BTS.model.UserRole;
import com.ded.BTS.repository.RoleRepo;
import com.ded.BTS.repository.UserRepo;
import com.ded.BTS.security.RegisterRequest;

@Service
@Transactional
public class UserService {

    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;

    
    
    
    
    public UserService(UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}





	public void createUser(RegisterRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setStatus(UserStatus.ACTIVE);

        for (String roleName : request.roles()) {

        	List<Role> role = roleRepository.findByName(roleName);
            if(role==null || role.get(0)==null) {
               new IllegalArgumentException("Role not found: " + roleName);
            }


            UserRole userRole = new UserRole();
            userRole.setRole(role.get(0));

            user.addUserRole(userRole); // sets both sides
        }

        userRepository.save(user);
    }
	
	
	@Transactional
	public void assignRoleToUser(String username, String roleName) {

	    User user = userRepository.findByUsernameWithRoles(username)
	            .orElseThrow(() -> 
	                new IllegalArgumentException("User not found: " + username));

	   List<Role> role = roleRepository.findByName(roleName);
	             if(role==null || role.isEmpty()) {
	               throw new IllegalArgumentException("Role not found: " + roleName);
	             }

	    // Prevent duplicate assignment
	    boolean alreadyAssigned = user.getUserRoles().stream()
	            .anyMatch(ur -> ur.getRole().getName().equals(roleName));

	    if (alreadyAssigned) {
	        throw new IllegalStateException("User already has role: " + roleName);
	    }

	    UserRole userRole = new UserRole();
	    userRole.setRole(role.get(0));

	    user.addUserRole(userRole); // sets both sides

	    userRepository.save(user);
	}


}
