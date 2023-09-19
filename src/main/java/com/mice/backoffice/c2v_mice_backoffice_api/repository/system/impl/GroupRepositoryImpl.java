package com.mice.backoffice.c2v_mice_backoffice_api.repository.system.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.DbUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminResponseEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.system.group.GroupResponseEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.system.GroupRepository;
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
public class GroupRepositoryImpl implements GroupRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Object> findAll(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_group_select", GroupResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }

    @Override
    public Object findAllById(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_group_select_for_detail", GroupResponseEntity.class);
        GroupResponseEntity groupResponseEntity = (GroupResponseEntity) DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();

        entityManager.clear();
        storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_group_select_for_menus", GroupResponseEntity.GroupMenuResponseEntity.class);
        storedProcedureQuery.registerStoredProcedureParameter("in_group_id", Integer.class, ParameterMode.IN).setParameter("in_group_id", params.get("in_group_id"));
        List<GroupResponseEntity.GroupMenuResponseEntity> groupMenuResponseEntity = storedProcedureQuery.getResultList();
        groupResponseEntity.setGroupMenus(groupMenuResponseEntity);
        return groupResponseEntity;
    }

    @Override
    public Object add(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_group_insert", GroupResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object modify(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_group_update", GroupResponseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getSingleResult();
    }

    @Override
    public Object delete(Map<String, Object> params) {
        return null;
    }

}
