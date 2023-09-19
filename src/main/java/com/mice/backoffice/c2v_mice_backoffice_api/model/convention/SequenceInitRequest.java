package com.mice.backoffice.c2v_mice_backoffice_api.model.convention;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SequenceInitRequest {
    private String codeName;
    private long currentSeq;
}
