package com.ded.BTS.DTO.request;

import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(
        name = "Add Comment Request",
        description = "Payload used to add a comment to a ticket"
)
public record AddCommentRequest(


        @NotBlank
        @Schema(
                description = "Comment message content",
                example = "This issue occurs only in production environment",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 1
        )
        String content
) {}
