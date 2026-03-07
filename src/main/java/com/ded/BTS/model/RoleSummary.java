package com.ded.BTS.model;

import com.ded.BTS.enums.RoleNames;
import com.ded.BTS.enums.RoleStatus;

public record RoleSummary(Long roleId, RoleNames roleName, RoleStatus roleStatus) {

}
