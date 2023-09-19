package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BannerDisplayListRequest {

    private Supports.BannerDisplaySearchDateType dateType;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Supports.BannerCode bannerCode;
    private Supports.BannerDisplayStateCodeForSearch bannerDisplayStateCode;
    private Supports.BannerDisplaySearchNameAndCodeType nameAndCodeType;
    private String nameAndCodeKeyword;
    private int pageNum;
    private int pageSize;
}
