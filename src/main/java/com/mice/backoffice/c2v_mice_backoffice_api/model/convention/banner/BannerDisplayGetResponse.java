package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class BannerDisplayGetResponse {


    /*banner*/
    private long bannerId;
    private String location;
    private String locationDiscription;
    private BigDecimal sizeX;
    private BigDecimal sizeY;
    private int bannerCode;

    /*bannerDisplay*/
    private Supports.BannerDisplayStateCode stateCode;
    private char alwaysDisplayYn;
    private LocalDateTime displayStartDatetime;
    private  LocalDateTime displayEndDatetime;
    private Map<Supports.LanguageType,String> displayContents;
    private Map<Supports.LanguageType,String> linkAddress;




}
