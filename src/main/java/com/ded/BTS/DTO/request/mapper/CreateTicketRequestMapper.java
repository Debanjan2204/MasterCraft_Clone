package com.ded.BTS.DTO.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ded.BTS.DTO.request.CreateTicketRequest;
import com.ded.BTS.enums.TicketPriority;
import com.ded.BTS.enums.TicketStatus;
import com.ded.BTS.enums.TicketType;
import com.ded.BTS.model.Ticket;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CreateTicketRequestMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "createdBy", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "updatedBy", ignore = true)
	@Mapping(target = "recStartDate", ignore = true)
	@Mapping(target = "recEndDate", ignore = true)
	@Mapping(target = "project", ignore = true)
	@Mapping(target = "assignee", ignore = true)
	@Mapping(target = "reporter", ignore = true)
	@Mapping(target = "type", source = "ticketType")
	@Mapping(target = "priority", source = "ticketPriority")
	@Mapping(target = "status", source = "ticketStatus")
	Ticket toEntity(CreateTicketRequest createTicketRequest);

	default TicketType mapTicketType(String value) {
		return TicketType.getvalueOf(value);
	}
	
	default TicketPriority mapTicketPriority(String value) {
		return TicketPriority.getvalueOf(value);
	}
	
	default TicketStatus mapTicketStatus(String value) {
		return TicketStatus.getvalueOf(value);
	}


}
