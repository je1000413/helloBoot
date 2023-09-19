package com.mice.backoffice.c2v_mice_backoffice_api.exception;

import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.mice.backoffice.c2v_mice_backoffice_api.common.Constants.*;

@Getter
@NoArgsConstructor
public class CustomException extends Exception{
    private ResponseCode responseCode;

    public CustomException(String message, ResponseCode code) {
        super(message);
        this.responseCode = code;
    }
}
