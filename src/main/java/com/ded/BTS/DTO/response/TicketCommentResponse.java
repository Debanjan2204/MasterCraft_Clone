package com.ded.BTS.DTO.response;

import com.ded.BTS.model.UserSummary;

public record TicketCommentResponse (
		
		Long id,
		UserSummary author,
		String content
		
		){

}
