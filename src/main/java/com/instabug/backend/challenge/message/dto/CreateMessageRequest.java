package com.instabug.backend.challenge.message.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CreateMessageRequest {

    @NotBlank(message = "Message body cannot be blank.")
    private String body;
}
