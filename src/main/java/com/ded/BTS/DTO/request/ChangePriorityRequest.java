package com.ded.BTS.DTO.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePriorityRequest(@NotBlank String priority) {

}
