package com.mice.backoffice.c2v_mice_backoffice_api.service.sample.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatKafkaSubServiceImpl{

    public static List<String> messageList = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

//    @KafkaListener(topics = "topic2", groupId = "mice")
    public void onMessage(String message) throws IOException {
        System.out.println("consume message = " + message);
    }

}
