package com.mice.backoffice.c2v_mice_backoffice_api.repository.system.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.DbUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.system.Menu.MenuResponseEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.system.Menu.MenuAuthorityResponseEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.system.MenuRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Object> findAll(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_menu_select", MenuResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }

    @Override
    public Object findAllById(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_menu_select_for_detail", MenuResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object add(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_menu_insert", MenuResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object modify(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_menu_update", MenuResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object delete(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_menu_delete", MenuResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }


    @Override
    public List<Object> findAuthorityAll(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_menu_authority_select", MenuAuthorityResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }

    @Override
    public Object addAuthority(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_menu_authority_insert", MenuAuthorityResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }

    @Override
    public Object modifyAuthority(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_menu_authority_update", MenuAuthorityResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }

    @Override
    public Object deleteAuthority(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_menu_authority_delete", MenuAuthorityResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }
}
