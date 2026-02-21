package com.ded.BTS.DTO.response.mapper;

import com.ded.BTS.DTO.response.TicketCommentResponse;
import com.ded.BTS.model.TicketComment;
import com.ded.BTS.model.UserSummary;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-21T19:43:57+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class TicketCommentResponseMapperImpl implements TicketCommentResponseMapper {

    @Override
    public TicketCommentResponse toResponse(TicketComment ticketComment) {
        if ( ticketComment == null ) {
            return null;
        }

        Long id = null;
        UserSummary author = null;
        String content = null;

        id = ticketComment.getId();
        author = map( ticketComment.getAuthor() );
        content = ticketComment.getContent();

        TicketCommentResponse ticketCommentResponse = new TicketCommentResponse( id, author, content );

        return ticketCommentResponse;
    }

    @Override
    public List<TicketCommentResponse> toResponseList(List<TicketComment> ticketComments) {
        if ( ticketComments == null ) {
            return null;
        }

        List<TicketCommentResponse> list = new ArrayList<TicketCommentResponse>( ticketComments.size() );
        for ( TicketComment ticketComment : ticketComments ) {
            list.add( toResponse( ticketComment ) );
        }

        return list;
    }
}
