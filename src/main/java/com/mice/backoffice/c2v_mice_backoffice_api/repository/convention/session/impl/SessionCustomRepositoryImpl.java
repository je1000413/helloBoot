package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.DbUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionForParentEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session.SessionCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SessionCustomRepositoryImpl implements SessionCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<SessionForParentEntity> findSessionForParent(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_session_select_for_parent", SessionForParentEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }
}
