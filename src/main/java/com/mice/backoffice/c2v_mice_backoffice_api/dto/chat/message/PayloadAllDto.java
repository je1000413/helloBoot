package com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message.MessageSendDto.CommentDto;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MessageCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MessageCreateRequest.PayloadCreateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PayloadAllDto implements Serializable {
    private long sendUserId;
    private String groupId;
    private List<CommentDto> comments;

    @Builder
    public PayloadAllDto(long sendUserId, String groupId, List<CommentDto> comments) {
        this.sendUserId = sendUserId;
        this.groupId = groupId;
        this.comments = comments;
    }

    public static PayloadAllDto makeInstance(PayloadCreateRequest req) {
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
