package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.event.EventPackageListDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EventPackageListResponse {
    private long totalCount;
    private int currentPageNum;
    private int lastPageNum;
    private int pageSize;
    private boolean isCom2verseUser;
    private Integer domainId;

    List<EventPackageListDetailResponse> packages;

    @Builder
    public EventPackageListResponse(long totalCount, int currentPageNum, int lastPageNum, int pageSize, boolean isCom2verseUser, Integer domainId, List<EventPackageListDetailResponse> packages) {
        this.totalCount = totalCount;
        this.currentPageNum = currentPageNum;
        this.lastPageNum = lastPageNum;
        this.pageSize = pageSize;
        this.isCom2verseUser = isCom2verseUser;
        this.domainId = domainId;
        this.packages = packages;
    }

    public static EventPackageListResponseBuilder dataBuilder(Page<EventPackageListDto> page) {
        return builder()
                .totalCount(page.getTotalElements())
                .currentPageNum(page.getNumber() + 1)
                .lastPageNum(page.getTotalPages())
                .pageSize(page.getSize());
    }
}
