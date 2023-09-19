package com.mice.backoffice.c2v_mice_backoffice_api.service.convention;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LoungeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge.LoungeCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge.LoungeListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge.LoungeUpdateRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface LoungeService {
    List<LoungeEntity> findAllByEventId(long eventId) throws C2VException;
    C2VResponse list(LoungeListRequest request) throws C2VException;
    C2VResponse get(int loungeNo) throws C2VException;
    C2VResponse create( LoungeCreateRequest request) throws C2VException, NoSuchAlgorithmException, IOException;
    C2VResponse update(int loungeNo, LoungeUpdateRequest request) throws C2VException, NoSuchAlgorithmException, IOException;
    C2VResponse delete(int loungeNo);
}