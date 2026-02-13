package com.ded.BTS.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ded.BTS.model.User;
import com.ded.BTS.repository.UserRepo;
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
		User user = userRepo.findByUsernameWithRoles(username).orElseThrow(()-> new RuntimeException("User Not Found"));
		
		return new CustomUserDetails(user);	}

}
