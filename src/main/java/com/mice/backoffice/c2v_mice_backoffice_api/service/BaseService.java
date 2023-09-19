package com.mice.backoffice.c2v_mice_backoffice_api.service;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import java.util.Map;

public interface BaseService {
    /**
     * 리스트조회
     **/
    C2VResponse findAll(Map<String, Object> params) throws C2VException;

    /**
     * 단건조회
     **/
    C2VResponse findAllById(Map<String, Object> params) throws C2VException;


    /**
     * 추가
     * **/
    C2VResponse add(Map<String, Object> params) throws C2VException;

    /**
     * 수정
     * **/
    C2VResponse modify(Map<String, Object> params) throws C2VException;

    /**
     * 삭제
     * **/
    C2VResponse delete(Map<String, Object> params) throws C2VException;
}
