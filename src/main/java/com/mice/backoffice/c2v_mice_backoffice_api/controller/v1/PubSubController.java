package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1;

import com.mice.backoffice.c2v_mice_backoffice_api.model.common.ChatMessage;
import com.mice.backoffice.c2v_mice_backoffice_api.service.sample.ChatKafkaPubService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.sample.ChatRedisPubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pubsub")
public class PubSubController {

    @Autowired
    private ChatRedisPubService chatRedisPubService;

    @Autowired
    private ChatKafkaPubService chatKafkaPubService;

//    @PostMapping("/redis")
//    public String pubSub(@RequestBody ChatMessage chatMessage) {
//        chatRedisPubService.sendMessage(chatMessage);
//        return "success";
//    }
//
//    @PostMapping("/kafka")
//    public String kafkaPubSub(@RequestParam("message") String message) {
//        chatKafkaPubService.sendMessage(message);
//        return "success";
//    }
}

