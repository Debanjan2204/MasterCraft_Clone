package com.ded.BTS.DTO.request;

import java.time.Instant;
import java.util.IntSummaryStatistics;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "Assign Ticket Request",
        description = "Payload used to assign a ticket to an user"
)
public record AssignTicketRequest(
		@NotBlank
		@Schema(
                description = "Unique identifier of the user",
                example = "john"
        )String userName) {

}
