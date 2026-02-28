package com.ded.BTS.DTO.request;

import java.time.Instant;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

@Schema(description = "Request payload for updating an existing ticket")
public record UpdateTicketRequest(

        @NotBlank
        @Schema(
                description = "Title of the ticket",
                example = "Fix login API failure",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String title,

        @Schema(
                description = "Detailed description of the ticket",
                example = "Users are unable to login after deployment"
        )
        String description,

        @NotBlank
        @Schema(
                description = "Type of the ticket",
                example = "BUG",
                allowableValues = {"BUG", "FEATURE", "TASK"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String ticketType,

        @Future
        @Schema(
                description = "Due date of the ticket in UTC (ISO-8601 format)",
                example = "2026-03-01T10:15:30Z",
                type = "string",
                format = "date-time"
        )
        Instant dueDate
) {}
