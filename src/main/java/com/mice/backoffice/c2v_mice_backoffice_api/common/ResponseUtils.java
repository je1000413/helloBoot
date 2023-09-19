package com.mice.backoffice.c2v_mice_backoffice_api.common;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

public class ResponseUtils {
    private ResponseUtils() {}
    public static void setResponseDefaultAttribute(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
    }
}
