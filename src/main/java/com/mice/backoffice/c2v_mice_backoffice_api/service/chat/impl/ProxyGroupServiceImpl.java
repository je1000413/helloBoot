package com.mice.backoffice.c2v_mice_backoffice_api.service.chat.impl;

import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.google.gson.Gson;
import com.mice.backoffice.c2v_mice_backoffice_api.client.GroupFeignClient;
import com.mice.backoffice.c2v_mice_backoffice_api.common.CommonUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.JoinGroupDto;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.GroupJoinRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.chat.ProxyGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyGroupServiceImpl implements ProxyGroupService {
    private final GroupFeignClient groupFeignClient;
    private final CommonUtils commonUtils;
    private final Gson gson;

    @Override
    public JoinGroupDto joinGroupArea(String groupId, String userId) throws Exception {
        GroupJoinRequest dto = GroupJoinRequest
                .builder()
                .groupId(groupId)
                .userId(userId)
                .build();

        C2VResponse response = groupFeignClient.joinGroupArea(commonUtils.getSystemToken(), dto);
        JoinGroupDto result = JoinGroupDto.makeInstance(response);

        if (response.getCode() != ResponseCode.OK.code()) {
            log.error(String.format("join group api call error: [%s]", gson.toJson(response)));
        }

        return result;
    }


    @Override
    public JoinGroupDto enterSpace(GroupJoinRequest groupJoinRequest) throws Exception {
        C2VResponse response = groupFeignClient.enterSpace(commonUtils.getSystemToken(), groupJoinRequest);
        JoinGroupDto result = JoinGroupDto.makeInstance(response);

        if (response.getCode() != ResponseCode.OK.code()) {
            log.error(String.format("space enter api call error: [%s]", gson.toJson(response)));
        }

        return result;
    }

    @Override
    public JoinGroupDto exitSpace(GroupJoinRequest groupJoinRequest) throws Exception {
        C2VResponse response = groupFeignClient.exitSpace(commonUtils.getSystemToken(), groupJoinRequest);
        JoinGroupDto result = JoinGroupDto.makeInstance(response);

        if (response.getCode() != ResponseCode.OK.code()) {
            log.error(String.format("space exit api call error: [%s]", gson.toJson(response)));
        }

        return result;
    }


}
