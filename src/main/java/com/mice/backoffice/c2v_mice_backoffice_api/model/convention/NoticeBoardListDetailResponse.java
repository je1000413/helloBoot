package com.mice.backoffice.c2v_mice_backoffice_api.model.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardArticleType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.NoticeBoardListDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NoticeBoardListDetailResponse {
    private long itemNo;
    private int boardId;
    private NoticeBoardArticleType articleType;
    private String articleTitle;
    private char mainYn;
    private String userName;
    private LocalDateTime dateTime;

    @Builder
    public NoticeBoardListDetailResponse(int boardId, long itemNo, NoticeBoardArticleType articleType, String articleTitle, char mainYn, String userName, LocalDateTime dateTime) {
        this.boardId = boardId;
        this.itemNo = itemNo;
        this.articleType = articleType;
        this.articleTitle = articleTitle;
        this.mainYn = mainYn;
        this.userName = userName;
        this.dateTime = dateTime;
    }

    public static NoticeBoardListDetailResponseBuilder dataBuilder(NoticeBoardListDto dto) {
        return builder()
                .boardId(dto.getBoardId())
                .articleType(dto.getArticleType())
                .articleTitle(dto.getArticleTitle())
                .mainYn(dto.getMainYn());
    }
}
