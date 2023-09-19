package com.mice.backoffice.c2v_mice_backoffice_api.repository.system;

import com.mice.backoffice.c2v_mice_backoffice_api.repository.BaseRepository;

import java.util.Map;

public interface CodeCustomRepository extends BaseRepository {

    Object CreateViewPlatformCode(Map<String, Object> params);

}
