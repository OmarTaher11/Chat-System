package com.instabug.backend.challenge.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatInfoDTO {
    private Long chatNumber;
    private String applicationToken;
}