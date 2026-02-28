package com.ded.BTS.DTO.request;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Change Priority Request", description = "Payload used to change the priority of a ticket")
public record ChangePriorityRequest(
		@NotBlank 
		@Schema(description = "Priority of the ticket", example = "HIGH" , requiredMode = Schema.RequiredMode.REQUIRED)
		String priority) {

}
