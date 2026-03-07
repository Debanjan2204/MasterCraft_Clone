package com.ded.BTS.model;

import java.util.List;

import com.ded.BTS.enums.UserStatus;

public record PendingUserDetail(
		Long userId, String userName, UserStatus status, List<RoleSummary> roleList
		
		) {

}
