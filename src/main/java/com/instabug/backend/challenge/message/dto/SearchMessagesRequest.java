package com.instabug.backend.challenge.message.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchMessagesRequest {

    @NotBlank(message = "Query cannot be blank.")
    private String query;
}
