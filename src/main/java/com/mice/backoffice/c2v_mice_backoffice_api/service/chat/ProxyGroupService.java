package com.mice.backoffice.c2v_mice_backoffice_api.service.chat;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.JoinGroupDto;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.GroupJoinRequest;

public interface ProxyGroupService {
    JoinGroupDto joinGroupArea(String groupId, String userId) throws Exception;

    JoinGroupDto enterSpace(GroupJoinRequest groupJoinRequest) throws Exception;

    JoinGroupDto exitSpace(GroupJoinRequest groupJoinRequest) throws Exception;
}
