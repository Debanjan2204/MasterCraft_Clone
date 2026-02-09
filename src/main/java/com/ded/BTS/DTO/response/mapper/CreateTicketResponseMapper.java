package com.ded.BTS.DTO.response.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ded.BTS.DTO.response.CreateTicketResponse;
import com.ded.BTS.model.Project;
import com.ded.BTS.model.ProjectSummary;
import com.ded.BTS.model.Ticket;
import com.ded.BTS.model.User;
import com.ded.BTS.model.UserSummary;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CreateTicketResponseMapper {

	@Mapping(target = "type", source = "type")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "status", source = "status")
    CreateTicketResponse toResponse(Ticket ticket);

    // -------- nested mappers --------

    default UserSummary map(User user) {
        if (user == null) return null;
        return new UserSummary(user.getId(), user.getUsername());
    }

    default ProjectSummary map(Project project) {
        if (project == null) return null;
        return new ProjectSummary(project.getId(), project.getName());
    }
}
