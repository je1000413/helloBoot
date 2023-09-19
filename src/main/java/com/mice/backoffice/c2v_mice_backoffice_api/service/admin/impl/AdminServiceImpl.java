package com.mice.backoffice.c2v_mice_backoffice_api.service.admin.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminListEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.AdminAuthRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.AdminCustomRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.admin.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminAuthRepository adminAuthRepository;

    private final AdminCustomRepository adminCustomRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Map<String, Object> params = new LinkedHashMap<>();

    @Override
    public C2VResponse checkAuth(Long curAdminId, Integer curMenuSeq, String method) throws UncategorizedSQLException {
        adminAuthRepository.checkAuth(curAdminId, curMenuSeq, method);
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("권한 조회 성공").build();
    }


    @Override
    public C2VResponse findAll(AdminListRequest req) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"        , RequestUtils.getCurAdminId());
        params.put("in_search_admin_type"   , req.getSearchAdminType());
        params.put("in_search_admin_value"  , req.getSearchAdminValue());
        params.put("in_page_size"           , req.getPageSize());
        params.put("in_page_num"            , req.getPageNum());

        List<Object> list = adminCustomRepository.findAll(params);

        var data = new HashMap<String, Object>();
        data.put("totalCount", list.size() > 0 ? ((AdminListEntity) list.get(0)).getTotalCount() : 0);
        data.put("list", list);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }

    @Override
    public C2VResponse findAllById(long adminId) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"  , RequestUtils.getCurAdminId());
        params.put("in_admin_id"      , adminId);
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(adminCustomRepository.findAllById(params)).build();
    }

    @Override
    public C2VResponse addAdmin(AdminRequest req) throws C2VException {
        String accountPassword = "{bcrypt}" + passwordEncoder.encode(req.getAccountPassword());

        params.clear();
        params.put("in_cur_admin_id"     , RequestUtils.getCurAdminId());
        params.put("in_account_name"     , req.getAccountName());
        params.put("in_account_password" , accountPassword);
        params.put("in_name"             , req.getName());
        params.put("in_domain_id"        , req.getDomainId());
        params.put("in_mail_address"     , req.getMailAddress());
        params.put("in_tel_no"           , req.getTelNo());
        params.put("in_group_id"         , req.getGroupId());
        params.put("in_state_code"       , req.getStateCode());
        params.put("in_nickname"       , req.getNickname());
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(adminCustomRepository.add(params)).build();
    }

    @Override
    public C2VResponse modifyAdmin(long adminId, AdminRequest req) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"     , RequestUtils.getCurAdminId());
        params.put("in_admin_id"         , adminId);
        //params.put("in_account_password" , req.getAccountPassword().isEmpty() ? "" : "{bcrypt}" + passwordEncoder.encode(req.getAccountPassword()));
        params.put("in_account_password" , "");
        params.put("in_name"             , req.getName());
        params.put("in_domain_id"        , req.getDomainId());
        params.put("in_mail_address"     , req.getMailAddress());
        params.put("in_tel_no"           , req.getTelNo());
        params.put("in_group_id"         , req.getGroupId());
        params.put("in_state_code"       , req.getStateCode());
        params.put("in_nickname"       , req.getNickname());
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(adminCustomRepository.modify(params)).build();
    }

    @Override
    public C2VResponse modifyAdminPassword(long adminId, AdminPasswordRequest req) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"     , RequestUtils.getCurAdminId());
        params.put("in_admin_id"         , adminId);
        params.put("in_account_password" , req.getAccountPassword().isEmpty() ? "" : "{bcrypt}" + passwordEncoder.encode(req.getAccountPassword()));
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(adminCustomRepository.modifyAdminPassword(params)).build();
    }

    @Override
    public C2VResponse deleteAdmin(long adminId) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"     , RequestUtils.getCurAdminId());
        params.put("in_admin_id"         , adminId);
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(adminCustomRepository.delete(params)).build();
    }

    @Override
    public C2VResponse findDomain(DomainSearchRequest req) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"     , RequestUtils.getCurAdminId());
        params.put("in_search_type"      , req.getSearchType().getValue());
        params.put("in_search_value"     , req.getSearchValue());
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(adminCustomRepository.findDomain(params)).build();
    }

    @Override
    public C2VResponse addDomain(DomainRequest req) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"     , RequestUtils.getCurAdminId());
        params.put("in_domain_name"      , req.getDomainName());
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(adminCustomRepository.addDomain(params)).build();
    }

    @Override
    public C2VResponse modifyDomain(int domainId, DomainRequest req) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_domain_id"       , domainId);
        params.put("in_domain_name"     , req.getDomainName());
        params.put("in_use_yn"          , req.getUseYn());
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(adminCustomRepository.modifyDomain(params)).build();
    }

    @Override
    public C2VResponse deleteDomain(int domainId) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_domain_id"       , domainId);
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(adminCustomRepository.deleteDomain(params)).build();
    }

    @Override
    public C2VResponse getAdminAccountNameCheck(String accountName) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"  , RequestUtils.getCurAdminId());
        params.put("in_account_name"  , accountName);
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(adminCustomRepository.getAdminAccountNameCheck(params)).build();
    }
}
