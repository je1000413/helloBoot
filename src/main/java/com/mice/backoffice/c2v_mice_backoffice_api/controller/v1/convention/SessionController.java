package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionListForParentRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
@AllArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public C2VResponse create(@RequestBody SessionCreateRequest req) {
        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }


    @Operation(summary = "연관세션 리스트", description = "프로그램 리스트 검색 결과 제공 - 연관세션 팝업용")
    @GetMapping("/parents")
    public C2VResponse findSessionForParent(SessionListForParentRequest req) throws C2VException {
        return sessionService.findSessionForParent(req);
    }

}
