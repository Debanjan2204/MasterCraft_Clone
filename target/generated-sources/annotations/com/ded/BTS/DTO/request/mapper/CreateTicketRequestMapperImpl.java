package com.ded.BTS.DTO.request.mapper;

import com.ded.BTS.DTO.request.CreateTicketRequest;
import com.ded.BTS.model.Ticket;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-16T21:57:41+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CreateTicketRequestMapperImpl implements CreateTicketRequestMapper {

    @Override
    public Ticket toEntity(CreateTicketRequest createTicketRequest) {
        if ( createTicketRequest == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setType( mapTicketType( createTicketRequest.ticketType() ) );
        ticket.setPriority( mapTicketPriority( createTicketRequest.ticketPriority() ) );
        ticket.setStatus( mapTicketStatus( createTicketRequest.ticketStatus() ) );
        ticket.setTitle( createTicketRequest.title() );
        ticket.setDescription( createTicketRequest.description() );
        ticket.setDueDate( createTicketRequest.dueDate() );

        return ticket;
    }
}
