package com.instabug.backend.challenge.redis.service;

import com.instabug.backend.challenge.application.repository.ApplicationRepository;
import com.instabug.backend.challenge.chat.dto.ChatInfoDTO;
import com.instabug.backend.challenge.chat.repository.ChatRepository;
import com.instabug.backend.challenge.utils.enums.RedisTokens;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisToDatabaseSyncService {

    private final RedisService redisService;
    private final ApplicationRepository applicationRepository;
    private final ChatRepository chatRepository;


    public RedisToDatabaseSyncService(RedisService redisService, ApplicationRepository applicationRepository, ChatRepository chatRepository) {
        this.redisService = redisService;
        this.applicationRepository = applicationRepository;
        this.chatRepository = chatRepository;
    }

    @Scheduled(fixedRateString = "${scheduled.task.fixedRate}")
    public void syncChatCountsToDatabase() {
        List<String> applicationTokens = applicationRepository.findAllTokens();
        for (String token : applicationTokens) {
            Long redisChatsCount = redisService.getCurrentValue(token, RedisTokens.COUNT.getTokenKey());
            applicationRepository.updateChatsCount(token, redisChatsCount);
        }
    }


    @Scheduled(fixedRateString = "${scheduled.task.fixedRate}")
    public void syncMessageCountsToDatabase() {
        List<ChatInfoDTO> results = chatRepository.findAllChatNumbersAndApplicationTokens();

        for (ChatInfoDTO chatInfo : results) {
            Long redisMessagesCount = redisService.getCurrentValue(chatInfo.getApplicationToken(), chatInfo.getChatNumber().toString(), RedisTokens.COUNT.getTokenKey());
            chatRepository.updateMessagesCountByNumberAndApplicationToken(chatInfo.getChatNumber(), chatInfo.getApplicationToken(), redisMessagesCount);
        }
    }
}
