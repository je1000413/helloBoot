package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sequences")
@Getter
public class SequencesEntity {
    @EmbeddedId
    private SequencesPrimaryKey sequencesPrimaryKey;

    @Builder
    public SequencesEntity(SequencesPrimaryKey sequencesPrimaryKey) {
        this.sequencesPrimaryKey = sequencesPrimaryKey;
    }

    @Getter
    @Embeddable
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SequencesPrimaryKey implements Serializable {
        private @Column(name = "code_name") String codeName;
        private @Column(name = "current_seq") long currentSeq;

        @Builder
        public SequencesPrimaryKey(String codeName, long currentSeq) {
            this.codeName = codeName;
            this.currentSeq = currentSeq;
        }
    }
}
