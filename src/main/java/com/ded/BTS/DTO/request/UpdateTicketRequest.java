package com.ded.BTS.DTO.request;

import java.time.Instant;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

public record UpdateTicketRequest(

	 @NotBlank	String title,

		String description,

	 @NotBlank	String ticketType,

		
	 @Future @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST") Instant dueDate

) {
};
