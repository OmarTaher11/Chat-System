package com.instabug.backend.challenge.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateApplicationRequest {

    @NotBlank(message = "Application name cannot be blank.")
    private String name;
}
