package com.mice.backoffice.c2v_mice_backoffice_api.model.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardSearchArticleType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardSearchDateType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardSearchRollingType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardSearchTitleAndCreatorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeBoardQueryParam {
    // 일자 별
    private NoticeBoardSearchDateType dateType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    // 공지 타입
    private NoticeBoardSearchArticleType articleType;

    // 롤링 여부
    private NoticeBoardSearchRollingType rollingType;

    // 제목/작성자
    private NoticeBoardSearchTitleAndCreatorType titleAndCreatorType;
    private String titleAndCreatorKeyword;

    // 페이지 넘버
    private int pageNum;

    // 페이지당 목록
    private int pageSize;

}
