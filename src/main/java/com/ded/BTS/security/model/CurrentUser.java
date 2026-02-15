package com.ded.BTS.security.model;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    public  String getLoggedInUserId() {
        CustomUserDetails userDetails=(CustomUserDetails)
            SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        
        
        return userDetails.getUsername();
    }
    

}