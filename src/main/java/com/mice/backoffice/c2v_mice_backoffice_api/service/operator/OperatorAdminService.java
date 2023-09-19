package com.mice.backoffice.c2v_mice_backoffice_api.service.operator;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminPasswordRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.LoginRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.OpAdminCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.OpAdminListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.OpAdminUpdateRequest;

public interface OperatorAdminService {
    C2VResponse login(LoginRequest loginRequest) throws C2VException;

    C2VResponse list(OpAdminListRequest request);

    C2VResponse get(long adminId);

    C2VResponse create(OpAdminCreateRequest request) throws C2VException;

    C2VResponse update(long adminId, OpAdminUpdateRequest request) throws C2VException;

    C2VResponse modifyAdminPassword(long adminId) throws C2VException;

    C2VResponse getAdminAccountNameCheck(String accountName) throws C2VException;

    C2VResponse modifyNewPassword(long adminId, AdminPasswordRequest req) throws C2VException;

    C2VResponse partnerLogin(LoginRequest loginRequest) throws C2VException;

    C2VResponse partnerAuth(LoginRequest loginRequest, int authNumber) throws C2VException;
}
