package com.ded.BTS.DTO.request.mapper;

import com.ded.BTS.DTO.request.CreateTicketRequest;
import com.ded.BTS.model.Ticket;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-14T21:58:36+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
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
