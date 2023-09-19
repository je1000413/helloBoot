package com.mice.backoffice.c2v_mice_backoffice_api.service.sample.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mice.backoffice.c2v_mice_backoffice_api.model.common.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubServiceImpl implements MessageListener {

    public static List<String> messageList = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            Object chatMessage = mapper.readValue(message.getBody(), Object.class);
            messageList.add(message.toString());

            System.out.println("받은 메시지 = " + message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
