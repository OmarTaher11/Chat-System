package com.instabug.backend.challenge.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatResponse {

    private Long number;
    private Long messagesCount;
}
