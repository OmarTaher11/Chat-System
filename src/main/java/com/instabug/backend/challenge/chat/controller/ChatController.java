package com.instabug.backend.challenge.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.instabug.backend.challenge.chat.dto.ChatResponse;
import com.instabug.backend.challenge.chat.service.ChatService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/applications/{applicationToken}/chats")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> createChat(@PathVariable String applicationToken) throws JsonProcessingException {
        ChatResponse chat = chatService.createChat(applicationToken);
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ChatResponse>> getChatsForApplication(@PathVariable String applicationToken,
                                                                     @RequestParam(defaultValue = "0") @Min(0) int page,
                                                                     @RequestParam(defaultValue = "10") @Positive int size) {
        Page<ChatResponse> chats = chatService.getChatsForApplication(applicationToken, page, size);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }
}
