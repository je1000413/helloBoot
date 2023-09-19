package com.mice.backoffice.c2v_mice_backoffice_api.service.login;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.redis.Token;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.LoginRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.LoginResponse;

import java.util.List;
import java.util.Map;

public interface LoginService {

    AdminEntity findByAccountNameAndAccountPassword(String accountName, String accountPassword) throws C2VException;

    AdminEntity findByAdminId(String adminId);

    List<LoginResponse> findLoginResponse(Long adminId);

    C2VResponse join(AdminEntity adminEntity) throws C2VException;

    C2VResponse login(LoginRequest loginRequest) throws C2VException;

    Map<String, Object> loginProcess(Long adminId, AdminEntity adminEntity);

    C2VResponse logout(String adminId);

    C2VResponse addToken(Token token);

    C2VResponse findRefreshTokenById(Long id);

    C2VResponse reissue(String id);

}