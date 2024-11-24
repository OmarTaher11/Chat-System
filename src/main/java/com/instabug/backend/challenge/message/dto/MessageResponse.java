package com.instabug.backend.challenge.message.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {

    private Long number;
    private String body;
}
