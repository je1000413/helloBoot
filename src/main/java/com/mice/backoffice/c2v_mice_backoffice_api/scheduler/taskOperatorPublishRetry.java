package com.mice.backoffice.c2v_mice_backoffice_api.scheduler;

import com.com2verse.platform.exception.C2VException;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Component
@RequiredArgsConstructor
public class taskOperatorPublishRetry {
    private final OperatorService operatorService;

    @Scheduled(fixedDelay = 1000)
    public void jobOperatorCommandRetry() {
        try {
            operatorService.jobOperatorCommandRetry();
        } catch (C2VException ex) {
            log.error(ex.getMessage());
        }
    }
}
