package com.ded.BTS.DTO.request;

import jakarta.validation.constraints.NotBlank;

public record AddCommentRequest(
		
		Long authorId,
	    @NotBlank	String content		
		) {

};
