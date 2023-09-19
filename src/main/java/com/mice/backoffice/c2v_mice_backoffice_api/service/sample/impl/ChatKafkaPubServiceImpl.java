package com.mice.backoffice.c2v_mice_backoffice_api.service.sample.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.service.sample.ChatKafkaPubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatKafkaPubServiceImpl implements ChatKafkaPubService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(String message) {
        System.out.println("produce message = " + message);
        kafkaTemplate.send("topic2", message);
    }
}
