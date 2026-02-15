package com.ded.BTS.DTO.request;

import java.time.Instant;
import java.util.IntSummaryStatistics;

import jakarta.validation.constraints.NotBlank;

public record AssignTicketRequest(
		@NotBlank String userName) {

}
