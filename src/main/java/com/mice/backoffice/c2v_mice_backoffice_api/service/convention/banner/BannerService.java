package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.banner;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner.BannerDisplayCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner.BannerDisplayListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner.BannerListRequest;



public interface BannerService {

    C2VResponse list(BannerListRequest request);
    C2VResponse displayList(BannerDisplayListRequest request);
    C2VResponse getDisplay(long displaySeq);
    C2VResponse createDisplay(BannerDisplayCreateRequest request) throws C2VException;
    C2VResponse deleteDisplay(long displaySeq);
    C2VResponse updateDisplay(long displaySeq, BannerDisplayCreateRequest request);


}
