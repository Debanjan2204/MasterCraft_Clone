package com.ded.BTS.security.model;

import java.util.List;


public record RegisterRequest(
	    String username,
	    String email,
	    String password,
	    String fullName,
	    List<String> roles
	) {}