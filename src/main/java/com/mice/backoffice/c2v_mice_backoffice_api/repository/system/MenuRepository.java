package com.mice.backoffice.c2v_mice_backoffice_api.repository.system;

import com.mice.backoffice.c2v_mice_backoffice_api.exception.RaisErrorException;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.BaseRepository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

public interface MenuRepository extends BaseRepository {

    @ExceptionHandler(RaisErrorException.class)
    List<Object> findAuthorityAll(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object addAuthority(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object modifyAuthority(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object deleteAuthority(Map<String, Object> params);
}
