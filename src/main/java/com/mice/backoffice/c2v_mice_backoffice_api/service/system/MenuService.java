package com.mice.backoffice.c2v_mice_backoffice_api.service.system;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.service.BaseService;

import java.util.Map;

public interface MenuService extends BaseService {


    /**
     * 역할 - 메뉴 리스트조회
     **/
    C2VResponse findMenuAuthorityAll(Map<String, Object> params) throws C2VException;
    /**
     * 역할 - 메뉴 등록
     **/
    C2VResponse addMenuAuthority(Map<String, Object> params) throws C2VException;
    /**
     * 역할 - 메뉴 수정
     **/
    C2VResponse modifyMenuAuthority(Map<String, Object> params) throws C2VException;
    /**
     * 역할 - 메뉴 삭제
     **/
    C2VResponse deleteMenuAuthority(Map<String, Object> params) throws C2VException;



}

