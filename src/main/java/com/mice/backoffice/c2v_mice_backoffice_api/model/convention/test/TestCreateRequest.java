package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.test;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TestCreateRequest {
    private LocalDateTime testStartTime;
    private LocalDateTime testEndTime;
    private String testString1;
    private String testString2;
}
