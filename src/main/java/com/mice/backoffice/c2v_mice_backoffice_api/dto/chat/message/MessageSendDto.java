package com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.MessageCommentType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.MessageSendType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.MessageType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MessageCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MessageCreateRequest.CommentCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MessageCreateRequest.PayloadCreateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MessageSendDto implements Serializable {
    private MessageType messageType;
    private Object payload;

    @Builder
    public MessageSendDto(MessageType messageType, Object payload) {
        this.messageType = messageType;
        this.payload = payload;
    }

    public static MessageSendDto makeInstance(MessageCreateRequest req) {
        Object payload = null;
        switch (req.getMessageSendType()) {
            case ALL -> payload = PayloadAllDto.makeInstance(req.getPayload());
            case LOCAL -> payload = PayloadLocalDto.makeInstance(req.getPayload());
            case WHISPER -> payload = PayloadWhisperDto.makeInstance(req.getPayload());
        }

        return builder()
                .messageType(req.getMessageType())
                .payload(payload)
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PayloadDto implements Serializable {
        private long sendUserId;
        private long receiverUserId;
        private String groupId;
        private List<CommentDto> comments;

        @Builder
        public PayloadDto(long sendUserId, long receiverUserId, String groupId, List<CommentDto> comments) {
            this.sendUserId = sendUserId;
            this.receiverUserId = receiverUserId;
            this.groupId = groupId;
            this.comments = comments;
        }

        public static PayloadDto makeInstance(PayloadCreateRequest req) {
            List<CommentDto> commentList = req
                    .getComments()
                    .stream()
                    .map(CommentDto::makeInstance)
                    .toList();

            return builder()
                    .sendUserId(req.getSendUserId())
                    .groupId("")
                    .comments(commentList)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommentDto implements Serializable {
        private MessageCommentType type;
        private Object payload;

        @Builder
        public CommentDto(MessageCommentType type, Object payload) {
            this.type = type;
            this.payload = payload;
        }

        public static CommentDto makeInstance(CommentCreateRequest req) {
            Object payload = null;

            switch (req.getMessageCommentType()){
                case NORMAL, URL, EMOTICON, CUSTOM_DATA -> payload = req.getMessage().get(0);
                case MENTION -> payload = req.getMessage();
                case FILE_CONTENTS -> {
                    Map<String, String> payloadMap = new HashMap<>();
                    payloadMap.put("fileType", "Image");
                    payloadMap.put("fileUrl", req.getMessage().get(0));

                    payload = payloadMap;
                }
            }

            return builder()
                    .type(req.getMessageCommentType())
                    .payload(payload)
                    .build();
        }
    }
}
