package com.mice.backoffice.c2v_mice_backoffice_api.repository;

import com.mice.backoffice.c2v_mice_backoffice_api.exception.RaisErrorException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

public interface BaseRepository {

    @ExceptionHandler(RaisErrorException.class)
    List<Object> findAll(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object findAllById(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object add(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object modify(Map<String, Object> params);

    @ExceptionHandler(RaisErrorException.class)
    Object delete(Map<String, Object> params);
}
