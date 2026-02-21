package com.ded.BTS.DTO.request.mapper;

import java.time.Instant;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.ded.BTS.DTO.request.UpdateTicketRequest;
import com.ded.BTS.enums.TicketType;
import com.ded.BTS.model.Ticket;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UpdateTicketRequestMapper {

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(target = "type", source = "ticketType")
	@Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "recStartDate", ignore = true)
    @Mapping(target = "recEndDate", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "reporter", ignore = true)
    @Mapping(target = "priority", ignore = true)
    @Mapping(target = "status", ignore = true)
	@Mapping(target = "originalState",ignore = true)
	void updateTicketFromDto(UpdateTicketRequest dto, @MappingTarget Ticket entity);

	default TicketType mapTicketType(String value) {
		return TicketType.getvalueOf(value);
	}
}
