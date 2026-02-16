package com.ded.BTS.security.model;

public record JwtResponse(
		
		String access_token,
	    String token_type,
	    long expires_in
		) {

	
	

}
