package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.chat;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.CommonUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.GroupJoinRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.chat.ProxyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proxy-groups")
public class ProxyGroupController {
    private final ProxyGroupService proxyGroupService;

    @PatchMapping("/{groupId}/join-users/{userId}")
    public C2VResponse joinGroupArea(@PathVariable("groupId") String groupId,
                                     @PathVariable("userId") String userId) throws Exception {
        Object result = proxyGroupService.joinGroupArea(groupId, userId);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(result)
                .build();
    }

    @PostMapping("/space-enter")
    public C2VResponse enterSpace(GroupJoinRequest groupJoinRequest) throws Exception {
        Object result = proxyGroupService.enterSpace(groupJoinRequest);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(result)
                .build();
    }

    @DeleteMapping("/space-exit")
    public C2VResponse exitSpace(GroupJoinRequest groupJoinRequest) throws Exception {
        Object result = proxyGroupService.exitSpace(groupJoinRequest);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(result)
                .build();
    }
}
