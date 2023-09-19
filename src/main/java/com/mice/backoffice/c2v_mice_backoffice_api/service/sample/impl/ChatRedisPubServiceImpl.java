package com.mice.backoffice.c2v_mice_backoffice_api.service.sample.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.model.common.ChatMessage;
import com.mice.backoffice.c2v_mice_backoffice_api.service.sample.ChatRedisPubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatRedisPubServiceImpl implements ChatRedisPubService {
    private final RedisTemplate<String, Object> redisTemplate;

    public ChatRedisPubServiceImpl(@Qualifier("redisTemplateByJsonSerializer") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessage(ChatMessage chatMessage) {
        redisTemplate.convertAndSend("topic1", chatMessage);
    }
}
