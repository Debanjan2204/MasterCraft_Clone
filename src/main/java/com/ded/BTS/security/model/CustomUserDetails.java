package com.ded.BTS.security.model;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ded.BTS.enums.UserStatus;
import com.ded.BTS.model.User;

public class CustomUserDetails implements UserDetails {

	private final User user;
	
	public CustomUserDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getUserRoles().stream().map(ur -> new SimpleGrantedAuthority(ur.getRole().getName())).toList();
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return user.getPasswordHash();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	
	@Override
    public boolean isEnabled() {
        return user.getStatus() == UserStatus.ACTIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus() == UserStatus.ACTIVE;
    }

    
    @Override 
    public boolean isAccountNonExpired() { return true; }
    
    
    @Override
    public boolean isCredentialsNonExpired() { return true; }
}


