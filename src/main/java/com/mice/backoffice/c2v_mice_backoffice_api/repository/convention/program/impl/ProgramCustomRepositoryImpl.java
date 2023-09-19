package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.program.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.DbUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramListEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.program.ProgramCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProgramCustomRepositoryImpl implements ProgramCustomRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<ProgramListEntity> findProgramForList(Map<String, Object> params) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_platform_program_select_for_paging", ProgramListEntity.class);
        return DbUtils.convertStoreProcParameters(storedProcedureQuery, params).getResultList();
    }

}
