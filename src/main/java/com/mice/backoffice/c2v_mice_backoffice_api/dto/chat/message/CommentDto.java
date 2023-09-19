package com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.MessageCommentType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MessageCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MessageCreateRequest.CommentCreateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto implements Serializable {
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
