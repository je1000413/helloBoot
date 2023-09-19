package com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.DbUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminListEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminResponseEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.DomainResponseEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.AdminCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AdminCustomRepositoryImpl implements AdminCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Object> findAll(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_admin_select_for_paging", AdminListEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }

    @Override
    public Object findAllById(Map<String, Object> params) {

        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_admin_select_for_detail", AdminResponseEntity.class);
        AdminResponseEntity adminResponseEntity = (AdminResponseEntity) DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();

        entityManager.clear();
        storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_admin_select_for_menus", AdminResponseEntity.AdminMenuResponseEntity.class);
        storedProcedureQuery.registerStoredProcedureParameter("in_admin_id", Long.class, ParameterMode.IN).setParameter("in_admin_id", params.get("in_admin_id"));
        List<AdminResponseEntity.AdminMenuResponseEntity> adminMenuResponseEntities = storedProcedureQuery.getResultList();
        adminResponseEntity.setAdminMenus(adminMenuResponseEntities);

        return adminResponseEntity;
    }

    @Override
    public Object add(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_admin_insert", AdminResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object modify(Map<String, Object> params){
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_admin_update", AdminResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object delete(Map<String, Object> params){
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_admin_delete", AdminResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }


    @Override
    public Object modifyAdminPassword(Map<String, Object> params){
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_admin_update_for_password", AdminResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }


    @Override
    public List<Object> findDomain(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_domain_select", DomainResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }

    @Override
    public Object addDomain(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_domain_insert", DomainResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object modifyDomain(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_domain_update", DomainResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object deleteDomain(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_domain_delete", DomainResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object getAdminAccountNameCheck(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_admin_select_for_accountName_check", AdminResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }


}
