package com.ded.BTS.security.model;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    public  String getLoggedInUserId() {
    	if(SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()!="anonymousUser") {
        CustomUserDetails userDetails=(CustomUserDetails)
            SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userDetails.getUsername();
    	}
    	else {
    		return "anonymousUser";
    	}
    	
        
        
    }
    
    public  CustomUserDetails getLoggedInUser() {
    	Object userObject=SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    	if(userObject!="anonymousUser") {
    	return (CustomUserDetails)userObject;
    	}else {return null ;}
            
    }

}