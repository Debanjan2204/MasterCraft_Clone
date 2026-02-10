package com.ded.BTS.DTO.response;

import java.time.Instant;

import com.ded.BTS.model.ProjectSummary;
import com.ded.BTS.model.UserSummary;
import com.fasterxml.jackson.annotation.JsonFormat;

public record TicketResponse(
		Long id, 
		ProjectSummary project,
		String title,
		String description,
		String type,
		String priority,
		String status,
		UserSummary reporter,
		UserSummary assignee,
		@JsonFormat(
		        shape = JsonFormat.Shape.STRING,
		        pattern = "yyyy-MM-dd HH:mm:ss",
		        timezone = "UTC"
		    )
		Instant dueDate,
		@JsonFormat(
		        shape = JsonFormat.Shape.STRING,
		        pattern = "yyyy-MMM-dd HH:mm:ss ",
		        timezone = "UTC"
		    )
		Instant createdAt,
		@JsonFormat(
		        shape = JsonFormat.Shape.STRING,
		        pattern = "yyyy-MMM-dd HH:mm:ss",
		        timezone = "UTC"
		    )
		Instant updatedAt
		) {
};