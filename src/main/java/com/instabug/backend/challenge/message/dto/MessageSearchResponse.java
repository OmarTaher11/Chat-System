package com.instabug.backend.challenge.message.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageSearchResponse {

    private Long messageId;
    private String body;

    protected boolean canEqual(final Object other) {
        return other instanceof MessageSearchResponse;
    }

}
