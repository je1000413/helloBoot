package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.SequencesEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.SequencesEntity.SequencesPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SequenceRepository extends JpaRepository<SequencesEntity, SequencesPrimaryKey> {
    SequencesEntity findBySequencesPrimaryKeyCodeName(String codeName);
    void deleteBySequencesPrimaryKeyCodeName(String codeName);
}
