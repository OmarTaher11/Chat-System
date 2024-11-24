package com.instabug.backend.challenge.queue.consumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instabug.backend.challenge.chat.entity.Chat;
import com.instabug.backend.challenge.chat.repository.ChatRepository;
import com.instabug.backend.challenge.redis.service.RedisService;
import com.instabug.backend.challenge.utils.enums.RedisTokens;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ChatConsumer {
    private final ObjectMapper objectMapper;
    private final ChatRepository chatRepository;
    private final RedisService redisService;

    public ChatConsumer(ObjectMapper objectMapper, ChatRepository chatRepository, RedisService redisService) {
        this.objectMapper = objectMapper;
        this.chatRepository = chatRepository;
        this.redisService = redisService;
    }

    @RabbitListener(queues = "chat-queue")
    public void handleChatCreationTask(String chatTask) {
        try {
            Chat chat = this.objectMapper.readValue(chatTask, Chat.class);
            chat.getApplication().setChatsCount(chat.getApplication().getChatsCount() + 1);
            Chat savedChat = this.chatRepository.save(chat);
            this.redisService.getNextNumber(savedChat.getApplicationToken(), RedisTokens.COUNT.getTokenKey());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing message: " + e.getMessage());
        }
    }
}
