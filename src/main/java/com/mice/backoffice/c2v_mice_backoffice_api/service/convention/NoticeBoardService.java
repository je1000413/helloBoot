package com.mice.backoffice.c2v_mice_backoffice_api.service.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.*;

public interface NoticeBoardService {
    public NoticeBoardListResponse list(NoticeBoardQueryParam cond) throws Exception;
    public NoticeBoardGetResponse get(int boardSeq) throws Exception;
    public NoticeBoardCreateResponse create(NoticeBoardCreateRequest req) throws Exception;
    public void update(NoticeBoardUpdateRequest req, int boardSeq) throws Exception;
    public void delete(int boardSeq) throws Exception;
}
