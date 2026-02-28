package com.ded.BTS.DTO.request;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectRequest(
		
		@NotBlank String projectKey,
		@NotBlank String name,
		String description
		) {

}
