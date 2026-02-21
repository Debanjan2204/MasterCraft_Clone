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
		
		Instant dueDate,
		
		Instant createdAt,
		
		Instant updatedAt
		) {
};