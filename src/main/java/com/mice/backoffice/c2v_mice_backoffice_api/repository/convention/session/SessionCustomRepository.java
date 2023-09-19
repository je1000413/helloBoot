package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionForParentEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.exception.RaisErrorException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

public interface SessionCustomRepository {

    @ExceptionHandler(RaisErrorException.class)
    List<SessionForParentEntity> findSessionForParent(Map<String, Object> params);


}
