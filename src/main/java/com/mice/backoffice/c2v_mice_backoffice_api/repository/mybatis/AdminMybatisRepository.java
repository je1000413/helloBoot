package com.mice.backoffice.c2v_mice_backoffice_api.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMybatisRepository {
    List<Map<String,Object>> findOperatorAdminListByCondition(Map<String,Object> request);
}
