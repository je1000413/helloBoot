package com.mice.backoffice.c2v_mice_backoffice_api.repository.admin;

import com.mice.backoffice.c2v_mice_backoffice_api.exception.RaisErrorException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Mapper
public interface AdminAuthRepository {
    @ExceptionHandler(RaisErrorException.class)
    void checkAuth(@Param("in_cur_admin_id")long curAdminId, @Param("in_cur_menu_seq")int curMenuSeq, @Param("in_method")String method);
}
