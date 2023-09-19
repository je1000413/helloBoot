package com.mice.backoffice.c2v_mice_backoffice_api.repository.login;

import com.com2verse.platform.exception.C2VException;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.LoginResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginResponseRepository {
    List<LoginResponse> findLoginResponse(@Param("in_admin_id") Long adminId);
}
