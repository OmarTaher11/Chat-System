package com.instabug.backend.challenge.message.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchMessagesResponse {

    private List<MessageResponse> messages;
}
