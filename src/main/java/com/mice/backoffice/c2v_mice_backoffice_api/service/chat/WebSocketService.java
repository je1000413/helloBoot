package com.mice.backoffice.c2v_mice_backoffice_api.service.chat;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.WebSocketConfigDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message.MessageSendDto;

public interface WebSocketService {
    public void sendMessage(MessageSendDto messageSendDto, WebSocketConfigDto webSocketConfigDto) throws Exception;
}
