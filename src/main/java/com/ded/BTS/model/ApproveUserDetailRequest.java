package com.ded.BTS.model;

import java.util.Map;

import com.ded.BTS.enums.RoleStatus;
import com.ded.BTS.enums.UserStatus;

import jakarta.annotation.Nullable;

public record ApproveUserDetailRequest(Long userId , @Nullable UserStatus toBeUpdatedUserStatus, Map<Long, RoleStatus> userRolePermMap) {

}
