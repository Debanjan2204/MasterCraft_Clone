package com.ded.BTS.DTO.request;

import java.time.Instant;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Change Due Date Request", description = "Payload Used to update the due-date of a ticket")
public record ChangeDueDateRequest( 
		@NotBlank 
		@Future 
		@Schema(description = "The date in which the ticket is expected to get resolved(Must be a future date)", example = "2026-03-12T12:20:36.220Z")
		Instant dueDate) {

}
