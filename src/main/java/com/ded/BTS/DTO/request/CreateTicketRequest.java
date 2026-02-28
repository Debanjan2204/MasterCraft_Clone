package com.ded.BTS.DTO.request;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Create Ticket Request", description = "Payload required to create a new ticket in the system")
public record CreateTicketRequest(

		@Schema(description = "Unique project identifier where ticket belongs", example = "1", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull Long projectId,

		@Schema(description = "Short summary of the issue", example = "Login API returning 500 error", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank String title,
		@Schema(description = "Detailed explanation of the issue", example = "User unable to login due to authentication failure") String description,

		@NotBlank @Schema(description = "Type of ticket", example = "BUG", allowableValues = {
				"BUG", "TASK", "STORY" }, requiredMode = Schema.RequiredMode.REQUIRED) String ticketType,

		@NotBlank @Schema(description = "Priority level assigned to the ticket", example = "HIGH", allowableValues = {
				"LOW", "MEDIUM", "HIGH" }, requiredMode = Schema.RequiredMode.REQUIRED) String ticketPriority,

		@NotBlank @Schema(description = "Current workflow status of the ticket", example = "OPEN", allowableValues = {
				"OPEN", "IN_PROGRESS", "RESOLVED",
				"CLOSED" }, requiredMode = Schema.RequiredMode.REQUIRED) String ticketStatus,

		@NotNull @Schema(description = "Username of the user assigned to handle the ticket", example = "john", requiredMode = Schema.RequiredMode.REQUIRED) String assigneeUserName,

		@Future 
		//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "IST") 
		@Schema(description = "Deadline for resolving the ticket (must be a future date)", example = "2026-03-01 18:30:00", type = "string", format = "date-time", requiredMode = Schema.RequiredMode.REQUIRED)
		Instant dueDate){
}
