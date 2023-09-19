package com.mice.backoffice.c2v_mice_backoffice_api.service.chat.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.MessageSendType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.WebSocketConfigDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message.MessageSendDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message.PayloadAllDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message.PayloadWhisperDto;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MessageCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.chat.ChatService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.chat.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {
    // Service
    private final WebSocketService webSocketService;

    @Override
    public void sendMessage(MessageCreateRequest req) throws Exception {
        // To-do
        // sessionId -> groupId로 변환 되는 로직 필요
        String groupId = "f9afc86d2cdf3437be6661451a679f1b";

        MessageSendDto dto = MessageSendDto.makeInstance(req);

        if (req.getMessageSendType() != MessageSendType.WHISPER)
            ((PayloadAllDto)dto.getPayload()).setGroupId(groupId);

        WebSocketConfigDto webSocketConfigDto = WebSocketConfigDto
                .builder()
                .appId("1")
                .deviceId("1")
                .userId(req.getPayload().getSendUserId())
                .build();

        webSocketService.sendMessage(dto, webSocketConfigDto);
    }
}
