package com.mice.backoffice.c2v_mice_backoffice_api.service.sample;

import com.mice.backoffice.c2v_mice_backoffice_api.model.common.ChatMessage;

public interface ChatRedisPubService {
    void sendMessage(ChatMessage chatMessage);
}
