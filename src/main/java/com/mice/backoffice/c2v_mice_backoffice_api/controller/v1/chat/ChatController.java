package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.chat;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MessageCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.chat.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    // Service
    private final ChatService chatService;

    @PostMapping("/message")
    public C2VResponse sendMessage(@RequestBody MessageCreateRequest req) throws Exception {
        chatService.sendMessage(req);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @PatchMapping("/block-users/{userId}")
    public C2VResponse blockUser(@PathVariable long userId) {
        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }
}
