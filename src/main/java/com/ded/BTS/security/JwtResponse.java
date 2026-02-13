package com.ded.BTS.security;

public record JwtResponse(
		
		String token,
		String message
		
		) {

	public JwtResponse(String token,String message) {

		this.message=message;
		this.token=token;
				
	}
	
	

}
