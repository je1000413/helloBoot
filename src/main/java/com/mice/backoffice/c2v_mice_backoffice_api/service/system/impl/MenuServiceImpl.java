package com.mice.backoffice.c2v_mice_backoffice_api.service.system.impl;


import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;

import com.mice.backoffice.c2v_mice_backoffice_api.repository.system.MenuRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.system.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public C2VResponse findAll(Map<String, Object> params) throws C2VException {

        /**
        if (params.get(Constants.ParameterNames.CLIENT_ID) == null) {
            throw new CommonException(ResponseCode.MISSING_PARAMETER);
        }
        **/

        params.clear();
        return getCommonResponse(menuRepository.findAll(params), log, params);
    }

    @Override
    public C2VResponse findAllById(Map<String, Object> params) throws C2VException {
        return getCommonResponse(menuRepository.findAllById(params), log, params);
    }

    @Override
    public C2VResponse add(Map<String, Object> params) throws C2VException {
        return getCommonResponse(menuRepository.add(params), log, params);
    }

    @Override
    public C2VResponse modify(Map<String, Object> params) throws C2VException {
        return getCommonResponse(menuRepository.modify(params), log, params);
    }

    @Override
    public C2VResponse delete(Map<String, Object> params) throws C2VException {
        return getCommonResponse(menuRepository.delete(params), log, params);
    }



    @Override
    public C2VResponse findMenuAuthorityAll(Map<String, Object> params) throws C2VException {
        return getCommonResponse(menuRepository.findAuthorityAll(params), log, params);
    }

    @Override
    public C2VResponse addMenuAuthority(Map<String, Object> params) throws C2VException {
        return getCommonResponse(menuRepository.addAuthority(params), log, params);
    }

    @Override
    public C2VResponse modifyMenuAuthority(Map<String, Object> params) throws C2VException {
        return getCommonResponse(menuRepository.modifyAuthority(params), log, params);
    }

    @Override
    public C2VResponse deleteMenuAuthority(Map<String, Object> params) throws C2VException {
        return getCommonResponse(menuRepository.deleteAuthority(params), log, params);
    }


    /**
     * 메소드 정의
     * **/
    static C2VResponse getCommonResponse(List<Object> data, Logger log, Map<String, Object> params) {

        C2VResponse c2VResponse = C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();

        return c2VResponse;
    }

    static C2VResponse getCommonResponse(Object data, Logger log, Map<String, Object> params) {

        C2VResponse c2VResponse = C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();

        return c2VResponse;
    }
}
