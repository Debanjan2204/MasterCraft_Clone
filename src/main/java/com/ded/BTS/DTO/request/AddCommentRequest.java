package com.ded.BTS.DTO.request;

public record AddCommentRequest(
		
		Long authorId,
		String content		
		) {

};
