package com.mice.backoffice.c2v_mice_backoffice_api.repository.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.operator.*;
import com.mice.backoffice.c2v_mice_backoffice_api.exception.RaisErrorException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

public interface OperatorRepository {

    @ExceptionHandler(RaisErrorException.class) List<OpEventEntity> getEventPrograms(Map<String, Object> params);
    @ExceptionHandler(RaisErrorException.class) List<OpProgramSessionEntity> getEventProgramSessions(Map<String, Object> params);
    @ExceptionHandler(RaisErrorException.class) List<OpSessionDetailEntity> getEventProgramDetail(Map<String, Object> params);
    @ExceptionHandler(RaisErrorException.class) List<OpSessionDisplayEntity> getEventProgramSessionDisplays(Map<String, Object> params);
    @ExceptionHandler(RaisErrorException.class) Object setEventProgramSessionChange(Map<String, Object> params);
    @ExceptionHandler(RaisErrorException.class) Object setEventProgramSessionStateUpdate(Map<String, Object> params);
    @ExceptionHandler(RaisErrorException.class) Object setEventProgramSessionMotionFavorite(Map<String, Object> params);
    @ExceptionHandler(RaisErrorException.class) List<OpMotionEntity> getEventProgramSessionMotionFavoriteList(Map<String, Object> params);
    @ExceptionHandler(RaisErrorException.class) Object setEventProgramStateChange(Map<String, Object> params);
    @ExceptionHandler(RaisErrorException.class) Object setEventStateChange(Map<String, Object> params);
    @ExceptionHandler(RaisErrorException.class) List<OpSessionIdEntity> getEventForSessionIdList(Map<String, Object> params);



}
