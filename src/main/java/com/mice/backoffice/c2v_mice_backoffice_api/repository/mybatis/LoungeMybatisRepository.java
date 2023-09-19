package com.mice.backoffice.c2v_mice_backoffice_api.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoungeMybatisRepository {
    List<Map<String,Object>> findLoungeListByRequest(Map<String,Object> request);

    List<Map<String, String>> findLoungeByLoungeCode(@Param("in_event_id")Long loungeCode);
}
