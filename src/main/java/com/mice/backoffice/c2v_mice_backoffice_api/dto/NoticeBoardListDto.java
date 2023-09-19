package com.mice.backoffice.c2v_mice_backoffice_api.dto;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardArticleType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class NoticeBoardListDto {
    private NoticeBoardArticleType articleType;
    private Integer boardId;
    private String articleTitle;
    private char mainYn;
    private AdminEntity createUser;
    private AdminEntity updateUser;
    private LocalDateTime updateDatetime;

    @QueryProjection
    public NoticeBoardListDto(Integer boardId, NoticeBoardArticleType articleType, String articleTitle, char mainYn, AdminEntity createUser, AdminEntity updateUser, LocalDateTime updateDatetime) {
        this.boardId = boardId;
        this.articleType = articleType;
        this.articleTitle = articleTitle;
        this.mainYn = mainYn;
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.updateDatetime = updateDatetime;
    }
}
