package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.chat;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.AudienceListQueryParam;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.AudienceListResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MicrophonePatchRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.chat.AudienceGroupService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/audience-manage/sessions/{sessionId}")
public class AudienceController {
    // Service
    private final AudienceGroupService audienceGroupService;


    @GetMapping("/all-users")
    public C2VResponse allUsers(@PathVariable("sessionId") long sessionId,
                            AudienceListQueryParam param) throws Exception {

        AudienceListResponse groupListResponse = audienceGroupService.groupList(sessionId, param);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(groupListResponse)
                .build();
    }

    @GetMapping("/question-users")
    public C2VResponse questionUsers(@PathVariable("sessionId") long sessionId) throws Exception {

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @DeleteMapping("/kick-out/{userId}")
    public C2VResponse kickOut(@PathVariable("sessionId") long sessionId,
                               @PathVariable("userId") long userId) throws Exception {

        audienceGroupService.kickOut(sessionId, userId);
        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @PatchMapping("/manage-microphone/{userId}")
    public C2VResponse microphone(@PathVariable("sessionId") long sessionId,
                                  @PathVariable("userId") long userId,
                                  @RequestBody MicrophonePatchRequest req) throws Exception {
        audienceGroupService.manageMicrophone(sessionId, userId, req);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @DeleteMapping("/question-users/{userId}")
    public C2VResponse delete(@PathVariable("sessionId") long sessionId,
                              @PathVariable("userId") long userId){
        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }
}