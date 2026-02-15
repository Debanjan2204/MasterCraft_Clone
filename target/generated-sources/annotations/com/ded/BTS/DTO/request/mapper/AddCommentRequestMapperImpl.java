package com.ded.BTS.DTO.request.mapper;

import com.ded.BTS.DTO.request.AddCommentRequest;
import com.ded.BTS.model.TicketComment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-15T15:37:17+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class AddCommentRequestMapperImpl implements AddCommentRequestMapper {

    @Override
    public TicketComment toEntity(AddCommentRequest addCommentRequest) {
        if ( addCommentRequest == null ) {
            return null;
        }

        TicketComment ticketComment = new TicketComment();

        ticketComment.setContent( addCommentRequest.content() );

        return ticketComment;
    }
}
