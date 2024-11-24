package com.instabug.backend.challenge.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationResponse {

    private String token;
    private String name;
    private Long chatsCount;
}
