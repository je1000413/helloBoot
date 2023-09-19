package com.mice.backoffice.c2v_mice_backoffice_api.model.chat;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.MessageCommentType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.MessageSendType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MessageCreateRequest {
    private MessageType messageType;
    private MessageSendType messageSendType;
    private PayloadCreateRequest payload;

    @Getter
    @NoArgsConstructor
    public static class PayloadCreateRequest {
        private long sendUserId;
        private long receiveUserId;
        private long sessionId;
        private String areaName;

        private List<CommentCreateRequest> comments;
    }

    @Getter
    @NoArgsConstructor
    public static class CommentCreateRequest {
        private MessageCommentType messageCommentType;
        private List<String> message;
    }
}
