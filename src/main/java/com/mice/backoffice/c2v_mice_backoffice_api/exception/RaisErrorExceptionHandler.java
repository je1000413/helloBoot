package com.mice.backoffice.c2v_mice_backoffice_api.exception;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants.ResponseCode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RaisErrorExceptionHandler {
    private final Map<String, Object> data = new LinkedHashMap<>();
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<C2VResponse> raisErrorExceptionHandling(SQLException exception) {
        data.put("code", -511);
        data.put("message", exception.getMessage());

        var c2VResponse = C2VResponse.builder().code(500).msg("INTERNAL_SERVER_ERROR").data(data).build();
        return ResponseEntity.status(c2VResponse.getCode()).body(c2VResponse);
    }
}
