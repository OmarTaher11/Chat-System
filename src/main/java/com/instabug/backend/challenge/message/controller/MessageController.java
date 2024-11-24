package com.instabug.backend.challenge.message.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.instabug.backend.challenge.message.dto.MessageResponse;
import com.instabug.backend.challenge.message.dto.MessageSearchResponse;
import com.instabug.backend.challenge.message.dto.UpdateMessageRequest;
import com.instabug.backend.challenge.message.dto.CreateMessageRequest;
import com.instabug.backend.challenge.message.service.MessageService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications/{applicationToken}/chats/{chatNumber}/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(@PathVariable String applicationToken,
                                                         @PathVariable Long chatNumber,
                                                         @RequestBody CreateMessageRequest request) throws JsonProcessingException {
        MessageResponse messageResponse = messageService.createMessage(applicationToken, chatNumber, request);
        return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<MessageResponse>> getMessagesForChat(@PathVariable String applicationToken,
                                                                    @PathVariable Long chatNumber,
                                                                    @RequestParam(defaultValue = "0")
                                                                    @Min(0)
                                                                    int page,
                                                                    @RequestParam(defaultValue = "10")
                                                                    @Positive
                                                                    int size) {
        Page<MessageResponse> messages = messageService.getMessagesForChat(applicationToken, chatNumber, page, size);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MessageSearchResponse>> searchMessages(@PathVariable String applicationToken,
                                                                      @PathVariable Long chatNumber,
                                                                      @RequestParam String query) {
        List<MessageSearchResponse> results = messageService.searchMessages(applicationToken, chatNumber, query);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{messageNumber}")
    public ResponseEntity<MessageResponse> updateMessage(
            @PathVariable String applicationToken,
            @PathVariable Long chatNumber,
            @PathVariable Long messageNumber,
            @RequestBody UpdateMessageRequest updatedMessage) throws JsonProcessingException {
        MessageResponse message = messageService.updateMessage(applicationToken, chatNumber, messageNumber, updatedMessage);
        return ResponseEntity.ok(message);
    }

}
