package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionQuestionListQueryParam;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionQuestionListResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionQuestionStateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionQuestionUserListQueryParam;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SessionQuestionController {
    // Service
    private final SessionQuestionService sessionQuestionService;

    @GetMapping("/sessions/{sessionId}/questions")
    public C2VResponse list(@PathVariable("sessionId") long sessionId,
                             SessionQuestionListQueryParam param) throws Exception {
        SessionQuestionListResponse sessionQuestionListResponse = sessionQuestionService.list(param, sessionId);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(sessionQuestionListResponse)
                .build();
    }

    @GetMapping("/sessions/{sessionId}/question-users")
    public C2VResponse list(@PathVariable("sessionId") long sessionId,
                            SessionQuestionUserListQueryParam param) throws Exception {
        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @PatchMapping("/sessions/{sessionId}/session-questions/{questionSeq}")
    public C2VResponse state(@PathVariable("sessionId") long sessionId,
                             @PathVariable("questionSeq") int questionSeq,
                             @RequestBody SessionQuestionStateRequest req) throws Exception {

        sessionQuestionService.changeState(req, sessionId, questionSeq);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }
}
