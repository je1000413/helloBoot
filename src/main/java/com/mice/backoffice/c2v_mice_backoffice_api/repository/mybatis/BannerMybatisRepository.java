package com.mice.backoffice.c2v_mice_backoffice_api.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BannerMybatisRepository {
    List<Map<String,Object>> findBannerListByCondition(Map<String,Object> request);

    List<Map<String, Object>> findBannerDisplayListByCondition(Map<String, Object> sqlRequest);
}
