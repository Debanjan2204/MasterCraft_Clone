package com.ded.BTS.DTO.response.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ded.BTS.enums.RoleNames;
import com.ded.BTS.enums.RoleStatus;
import com.ded.BTS.model.PendingUserDetail;
import com.ded.BTS.model.RoleSummary;
import com.ded.BTS.model.User;
import com.ded.BTS.model.UserRole;
import com.ded.BTS.model.UserSummary;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PendingUserSummaryMapper {

	@Mapping(target = "userId",source = "id")
	@Mapping(target = "userName", source = "username")
	@Mapping(target = "roleList" ,source = ".")
	public PendingUserDetail toDto(User user);
	
	default List<RoleSummary> map(User user) {
		if (user == null)
			return null;
		List<RoleSummary> roleList = new ArrayList<>();
		
		for (UserRole uRole : user.getUserRoles()) {
			if (uRole != null) {
				Long id = uRole.getId();
				RoleNames roleName = uRole.getRole().getName();
				RoleStatus status =  uRole.getStatus();
				roleList.add(new RoleSummary(id, roleName, status));
			}
		}
		return roleList;
	}
	
	public List<PendingUserDetail> toDtoList(List<User> users);
}
