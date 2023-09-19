package com.mice.backoffice.c2v_mice_backoffice_api.repository.admin;


import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminListEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.exception.RaisErrorException;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminCustomRepository extends BaseRepository {

    @ExceptionHandler(RaisErrorException.class)
    List<Object> findDomain(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object addDomain(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object modifyDomain(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object deleteDomain(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object modifyAdminPassword(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object getAdminAccountNameCheck(Map<String, Object> params);
}
