package com.ded.BTS.DTO.request;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTicketRequest(

		@NotNull Long projectId,

		@NotBlank String title,

		String description,

		@NotBlank String ticketType,

		@NotBlank String ticketPriority,

		@NotBlank String ticketStatus,

		@NotNull Long reporterId,

		@NotNull Long assigneeId,

		@Future @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC") Instant dueDate) {
};
