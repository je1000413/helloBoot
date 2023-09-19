package com.mice.backoffice.c2v_mice_backoffice_api.service.chat;

import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MessageCreateRequest;

public interface ChatService {
    public void sendMessage(MessageCreateRequest req) throws Exception;
}
