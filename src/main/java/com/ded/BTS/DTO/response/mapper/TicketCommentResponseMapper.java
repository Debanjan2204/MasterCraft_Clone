package com.ded.BTS.DTO.response.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ded.BTS.DTO.response.TicketCommentResponse;
import com.ded.BTS.model.TicketComment;
import com.ded.BTS.model.User;
import com.ded.BTS.model.UserSummary;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TicketCommentResponseMapper {

	@Mapping( target = "time",source = "createdAt")
	TicketCommentResponse toResponse(TicketComment ticketComment);

	default UserSummary map(User user) {
		if (user == null)
			return null;
		return new UserSummary(user.getId(), user.getUsername());
	}

	List<TicketCommentResponse> toResponseList(List<TicketComment> ticketComments);
	
}
