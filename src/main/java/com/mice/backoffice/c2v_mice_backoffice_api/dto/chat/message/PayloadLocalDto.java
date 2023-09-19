package com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.message.MessageSendDto.CommentDto;
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
public class PayloadLocalDto implements Serializable {
    private long sendUserId;
    private String areaName;
    private List<CommentDto> comments;

    @Builder
    public PayloadLocalDto(long sendUserId, String areaName, String groupId, List<CommentDto> comments) {
        this.sendUserId = sendUserId;
        this.areaName = areaName;
        this.comments = comments;
    }

    public static PayloadLocalDto makeInstance(PayloadCreateRequest req) {
        List<CommentDto> commentList = req
                .getComments()
                .stream()
                .map(CommentDto::makeInstance)
                .toList();

        return builder()
                .sendUserId(req.getSendUserId())
                .areaName(req.getAreaName())
                .comments(commentList)
                .build();
    }
}
