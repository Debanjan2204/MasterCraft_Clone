package com.ded.BTS.enums;

import com.ded.BTS.Exceptions.InvalidEnumException;

public enum RoleNames {

	ROLE_DEVELOPER, ROLE_ADMIN, ROLE_TESTER;

	public static RoleNames getvalueOf(String value) {
		try {
			return RoleNames.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new InvalidEnumException(RoleNames.class, value);
		}
	}
}
