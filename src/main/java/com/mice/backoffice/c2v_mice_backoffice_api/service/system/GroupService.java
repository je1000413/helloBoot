package com.mice.backoffice.c2v_mice_backoffice_api.service.system;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.system.GroupListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.system.GroupRequest;

public interface GroupService {
    C2VResponse findAll(GroupListRequest req) throws C2VException;
    C2VResponse findAllById(int groupId) throws C2VException;
    C2VResponse addGroup(GroupRequest req) throws C2VException;
    C2VResponse modifyGroup(int groupId, GroupRequest req) throws C2VException;
    C2VResponse deleteGroup(int groupId) throws C2VException;
}
