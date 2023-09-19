package com.mice.backoffice.c2v_mice_backoffice_api.repository.system.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.DbUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.system.CodeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.system.CodeCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeCustomRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Object> findAll(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_sys_code_info_select", CodeEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }

    @Override
    public Object findAllById(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_sys_code_info_select", CodeEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object add(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_temp_code_insert", CodeEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }

    @Override
    public Object modify(Map<String, Object> params){
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_sys_code_info_update", CodeEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object delete(Map<String, Object> params){
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_sys_code_info_delete", CodeEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object CreateViewPlatformCode(Map<String, Object> params){
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_viewCodeList_forCreate", CodeEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }
}
