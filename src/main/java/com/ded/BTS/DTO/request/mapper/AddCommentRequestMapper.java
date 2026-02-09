package com.ded.BTS.DTO.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ded.BTS.DTO.request.AddCommentRequest;
import com.ded.BTS.model.TicketComment;
import com.ded.BTS.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AddCommentRequestMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "recStartDate", ignore = true)
	@Mapping(target = "recEndDate", ignore = true)
	@Mapping(target = "author", ignore = true)
	@Mapping(target = "ticket",ignore = true)
	TicketComment toEntity(AddCommentRequest addCommentRequest);
}
