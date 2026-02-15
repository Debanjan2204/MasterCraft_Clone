package com.ded.BTS.DTO.request;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public record UpdateTicketRequest(

		String title,

		String description,

		String ticketType,

		@JsonFormat(
		        shape = JsonFormat.Shape.STRING,
		        pattern = "yyyy-MM-dd HH:mm:ss",
		        timezone = "IST"
		    )
		Instant dueDate

) {
};
