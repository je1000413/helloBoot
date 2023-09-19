package com.mice.backoffice.c2v_mice_backoffice_api.repository.operator.Impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.DbUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.BaseEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.operator.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.operator.OperatorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class OperatorRepositoryImpl implements OperatorRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<OpEventEntity> getEventPrograms(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_event_select", OpEventEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }

    @Override
    public List<OpProgramSessionEntity> getEventProgramSessions(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_program_select_for_sessiontimeline", OpProgramSessionEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }
    @Override
    public List<OpSessionDetailEntity> getEventProgramDetail(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_session_select_for_detail", OpSessionDetailEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }
    @Override
    public List<OpSessionDisplayEntity> getEventProgramSessionDisplays(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_session_select_for_source", OpSessionDisplayEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }
    @Override
    public Object setEventProgramSessionChange(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_session_update_for_change", BaseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }
    @Override
    public Object setEventProgramSessionStateUpdate(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_session_update_for_state_code", BaseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }


    @Override
    public Object setEventProgramSessionMotionFavorite(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_admin_motion_upset", BaseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }

    @Override
    public List<OpMotionEntity> getEventProgramSessionMotionFavoriteList(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_admin_motion_select", OpMotionEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }

    @Override
    public Object setEventProgramStateChange(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_program_update_for_session_finish", BaseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }

    @Override
    public Object setEventStateChange(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_event_update_for_state_code", BaseEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).execute();
    }

    @Override
    public List<OpSessionIdEntity> getEventForSessionIdList(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_op_event_select_for_session", OpSessionIdEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }


}
