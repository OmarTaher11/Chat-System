package com.instabug.backend.challenge.message.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessageDto {
    private String applicationToken;
    private Long chatNumber;
    private Long messageNumber;
    private UpdateMessageRequest updatedMessage;
}
