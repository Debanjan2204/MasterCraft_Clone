package com.ded.BTS.Exceptions;

import java.util.List;

import com.ded.BTS.enums.RoleNames;

public class RoleAlreadyAssignedException extends RuntimeException {

    private final List<RoleNames> duplicateRoles;

    public RoleAlreadyAssignedException(List<RoleNames> duplicateRoles) {
        super("Roles already assigned: " + duplicateRoles);
        this.duplicateRoles = duplicateRoles;
    }

    public List<RoleNames> getDuplicateRoles() {
        return duplicateRoles;
    }
}
