package com.ded.BTS.DTO.request.mapper;

import com.ded.BTS.DTO.request.UpdateTicketRequest;
import com.ded.BTS.model.Ticket;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-14T21:58:36+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class UpdateTicketRequestMapperImpl implements UpdateTicketRequestMapper {

    @Override
    public void updateTicketFromDto(UpdateTicketRequest dto, Ticket entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.ticketType() != null ) {
            entity.setType( mapTicketType( dto.ticketType() ) );
        }
        if ( dto.title() != null ) {
            entity.setTitle( dto.title() );
        }
        if ( dto.description() != null ) {
            entity.setDescription( dto.description() );
        }
        if ( dto.dueDate() != null ) {
            entity.setDueDate( dto.dueDate() );
        }
    }
}
