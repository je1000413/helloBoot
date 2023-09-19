package com.mice.backoffice.c2v_mice_backoffice_api.service.system.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.system.GroupListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.system.GroupRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.system.GroupRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.system.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private Map<String, Object> params = new LinkedHashMap<>();

    @Override
    public C2VResponse findAll(GroupListRequest req) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_search_type"       , req.getSearchType());
        params.put("in_search_value"      , req.getSearchValue());

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(groupRepository.findAll(params)).build();
    }

    @Override
    public C2VResponse findAllById(int groupId) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"  , RequestUtils.getCurAdminId());
        params.put("in_group_id"      , groupId);
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(groupRepository.findAllById(params)).build();
    }

    @Override
    public C2VResponse addGroup(GroupRequest req) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_group_name"      , req.getGroupName());
        params.put("in_role_code"       , req.getRoleCode());
        params.put("in_state_code"      , req.getStateCode());
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(groupRepository.add(params)).build();
    }

    @Override
    public C2VResponse modifyGroup(int groupId, GroupRequest req) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_group_id"        , groupId);
        params.put("in_group_name"      , req.getGroupName());
        params.put("in_role_code"       , req.getRoleCode());
        params.put("in_state_code"      , req.getStateCode());

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(groupRepository.modify(params)).build();
    }

    @Override
    public C2VResponse deleteGroup(int groupId) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_group_id"        , groupId);
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(groupRepository.delete(params)).build();
    }
}
