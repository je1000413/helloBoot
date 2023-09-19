package com.mice.backoffice.c2v_mice_backoffice_api.service.chat;

import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.AudienceListQueryParam;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.AudienceListResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.chat.MicrophonePatchRequest;

public interface AudienceGroupService {
    public AudienceListResponse groupList(long sessionId, AudienceListQueryParam param) throws Exception;
    public void kickOut(long sessionId, long userId) throws Exception;
    public void manageMicrophone(long sessionId, long userId, MicrophonePatchRequest req) throws Exception;
}
