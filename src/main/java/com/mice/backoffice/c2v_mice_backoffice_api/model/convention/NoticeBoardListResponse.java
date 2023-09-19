package com.mice.backoffice.c2v_mice_backoffice_api.model.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.NoticeBoardListDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NoticeBoardListResponse {
    private long totalCount;
    private int currentPageNum;
    private int lastPageNum;
    private int pageSize;

    private List<NoticeBoardListDetailResponse> noticeBoards;

    @Builder
    public NoticeBoardListResponse(long totalCount, int currentPageNum, int lastPageNum, int pageSize, List<NoticeBoardListDetailResponse> noticeBoards) {
        this.totalCount = totalCount;
        this.currentPageNum = currentPageNum;
        this.lastPageNum = lastPageNum;
        this.pageSize = pageSize;
        this.noticeBoards = noticeBoards;
    }

    public static NoticeBoardListResponseBuilder dataBuilder(Page<NoticeBoardListDto> page) {
        return builder()
                .totalCount(page.getTotalElements())
                .currentPageNum(page.getNumber() + 1)
                .lastPageNum(page.getTotalPages())
                .pageSize(page.getSize());
    }
}
