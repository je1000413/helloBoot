package com.mice.backoffice.c2v_mice_backoffice_api.exception;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Map<String, Object> data = new LinkedHashMap<>();

    @ExceptionHandler(CustomException.class)
    public C2VResponse customExceptionHandling(CustomException exception) {
        data.put("code", -511);
        data.put("message", exception.getMessage());

        return C2VResponse
                .builder()
                .code(exception.getResponseCode().value())
                .msg(exception.getResponseCode().toString())
                .data(data)
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public C2VResponse entityNotFoundExceptionHandling(EntityNotFoundException exception) {
        data.put("code", -511);
        data.put("message", exception.getMessage());

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.FAIL_INTERNAL_SERVER_ERROR.value())
                .msg(Constants.ResponseCode.FAIL_INTERNAL_SERVER_ERROR.toString())
                .data(data)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public C2VResponse exceptionHandling(Exception exception) {
        data.put("code", -511);
        data.put("message", exception.getMessage());

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.FAIL_INTERNAL_SERVER_ERROR.value())
                .msg(Constants.ResponseCode.FAIL_INTERNAL_SERVER_ERROR.toString())
                .data(data)
                .build();
    }
}
