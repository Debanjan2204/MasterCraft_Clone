package com.ded.BTS.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.User;
import com.ded.BTS.repository.UserRepo;
import com.ded.BTS.security.model.CustomUserDetails;

import jakarta.persistence.EntityNotFoundException;
@Service
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepo userRepo;
	
	
	
	public CustomUserDetailService(UserRepo userRepo) {
		super();
		this.userRepo = userRepo;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepo.findByUsernameWithRoles(username, UserStatus.ACTIVE)
				.orElseThrow(() -> new EntityNotFoundException(
						"Active User with username " + username + " not found"));
		
		return new CustomUserDetails(user);	}

}
