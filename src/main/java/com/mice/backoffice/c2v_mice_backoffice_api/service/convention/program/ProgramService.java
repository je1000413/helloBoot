package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.program;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program.*;

public interface ProgramService {
    ProgramGetResponse get(long programId) throws Exception;
    ProgramEntity findByProgramId(long programId) throws Exception;
    ProgramCreateResponse create(ProgramCreateRequest req) throws Exception;
    void update(long programId, ProgramUpdateRequest req) throws Exception;
    void delete(long programId) throws Exception;
    C2VResponse findProgramForList(ProgramListRequest req) throws Exception;
}
