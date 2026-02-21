package com.ded.BTS.DTO.request;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

public record ChangeDueDateRequest( @NotBlank @Future Instant dueDate) {

}
