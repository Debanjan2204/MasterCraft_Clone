package com.ded.BTS.model;

public record UserSummary(
		Long userId,
		String userName
		) {public static UserSummary from(User user) {
	        if (user == null) return null;
	        return new UserSummary(user.getId(), user.getUsername());
	    }}
