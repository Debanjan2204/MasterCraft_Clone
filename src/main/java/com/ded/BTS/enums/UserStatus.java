package com.ded.BTS.enums;

import com.ded.BTS.Exceptions.InvalidEnumException;

public enum UserStatus {
	ACTIVE, LOCKED, UNVERIFIED, DELETED;

	public static UserStatus getvalueOf(String value) {
		try {
			return UserStatus.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new InvalidEnumException(UserStatus.class, value);
		}
	}
}
