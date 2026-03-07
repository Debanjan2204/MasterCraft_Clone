package com.ded.BTS.Exceptions;

import java.util.List;

import com.ded.BTS.enums.RoleNames;

public class RoleNotFoundException extends RuntimeException {

	private final List<RoleNames> missingRoles;

	public RoleNotFoundException(List<RoleNames> missingRoles) {
		super("Roles not found: " + missingRoles);
		this.missingRoles = missingRoles;
	}

	public List<RoleNames> getMissingRoles() {
		return missingRoles;
	}
}