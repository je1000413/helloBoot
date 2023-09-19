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
public class PayloadWhisperDto implements Serializable {
    private long sendUserId;
    private long receiveUserId;
    private List<CommentDto> comments;

    @Builder
    public PayloadWhisperDto(long sendUserId, long receiveUserId, String groupId, List<CommentDto> comments) {
        this.sendUserId = sendUserId;
        this.receiveUserId = receiveUserId;
        this.comments = comments;
    }

    public static PayloadWhisperDto makeInstance(PayloadCreateRequest req) {
        List<CommentDto> commentList = req
                .getComments()
                .stream()
                .map(CommentDto::makeInstance)
                .toList();

        return builder()
                .sendUserId(req.getSendUserId())
                .receiveUserId(req.getReceiveUserId())
                .comments(commentList)
                .build();
    }
}
