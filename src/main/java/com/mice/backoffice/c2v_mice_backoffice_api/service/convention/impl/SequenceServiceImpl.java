package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.SequencesEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.SequencesEntity.SequencesPrimaryKey;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.SequenceRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.SequenceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class SequenceServiceImpl implements SequenceService {
    // Repository
    private final SequenceRepository sequenceRepository;

    @Override
    @Transactional
    public void initSequence(String codeName, long seq) {
        sequenceRepository.deleteBySequencesPrimaryKeyCodeName(codeName);

        SequencesPrimaryKey sequencesPrimaryKey = SequencesPrimaryKey
                .builder()
                .codeName(codeName)
                .currentSeq(seq)
                .build();

        SequencesEntity sequencesEntity = SequencesEntity
                .builder()
                .sequencesPrimaryKey(sequencesPrimaryKey)
                .build();

        sequenceRepository.save(sequencesEntity);
    }
}
