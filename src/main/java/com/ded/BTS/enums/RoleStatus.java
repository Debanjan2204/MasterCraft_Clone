package com.ded.BTS.enums;

import com.ded.BTS.Exceptions.InvalidEnumException;

public enum RoleStatus {
	REQUESTED, APPROVED, REJECTED;

	public static RoleStatus getvalueOf(String value) {
		try {
			return RoleStatus.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new InvalidEnumException(RoleStatus.class, value);
		}
	}
}
