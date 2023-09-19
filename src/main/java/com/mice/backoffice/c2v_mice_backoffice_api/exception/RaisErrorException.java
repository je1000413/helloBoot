package com.mice.backoffice.c2v_mice_backoffice_api.exception;

import java.sql.SQLException;

public class RaisErrorException extends SQLException {
    public RaisErrorException(String message) {
        super(message);
    }
}
