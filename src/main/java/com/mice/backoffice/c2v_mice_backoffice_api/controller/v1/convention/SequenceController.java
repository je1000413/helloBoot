package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SequenceInitRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.SequenceService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/sequences")
public class SequenceController {
    // Services
    private final SequenceService sequenceService;

    @PostMapping
    public C2VResponse init(@RequestBody SequenceInitRequest initReq) {
        sequenceService.initSequence(initReq.getCodeName(), initReq.getCurrentSeq());

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }
}
