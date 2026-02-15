package com.ded.BTS.DTO.response.mapper;

import com.ded.BTS.DTO.response.CreateTicketResponse;
import com.ded.BTS.model.ProjectSummary;
import com.ded.BTS.model.Ticket;
import com.ded.BTS.model.UserSummary;
import java.time.Instant;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-15T21:29:28+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CreateTicketResponseMapperImpl implements CreateTicketResponseMapper {

    @Override
    public CreateTicketResponse toResponse(Ticket ticket) {
        if ( ticket == null ) {
            return null;
        }

        String type = null;
        String priority = null;
        String status = null;
        Long id = null;
        ProjectSummary project = null;
        String title = null;
        String description = null;
        UserSummary reporter = null;
        UserSummary assignee = null;
        Instant dueDate = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        if ( ticket.getType() != null ) {
            type = ticket.getType().name();
        }
        if ( ticket.getPriority() != null ) {
            priority = ticket.getPriority().name();
        }
        if ( ticket.getStatus() != null ) {
            status = ticket.getStatus().name();
        }
        id = ticket.getId();
        project = map( ticket.getProject() );
        title = ticket.getTitle();
        description = ticket.getDescription();
        reporter = map( ticket.getReporter() );
        assignee = map( ticket.getAssignee() );
        dueDate = ticket.getDueDate();
        createdAt = ticket.getCreatedAt();
        updatedAt = ticket.getUpdatedAt();

        CreateTicketResponse createTicketResponse = new CreateTicketResponse( id, project, title, description, type, priority, status, reporter, assignee, dueDate, createdAt, updatedAt );

        return createTicketResponse;
    }
}
