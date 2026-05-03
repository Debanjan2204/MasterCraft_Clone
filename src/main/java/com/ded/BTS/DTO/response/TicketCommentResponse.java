package com.ded.BTS.DTO.response;

import com.ded.BTS.model.UserSummary;
import java.time.Instant;
public record TicketCommentResponse (
		
		Long id,
		UserSummary author,
		String content,
		Instant time
		
		){

}
