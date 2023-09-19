package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProgramCreateResponse {
    private long programId;

    @Builder
    public ProgramCreateResponse(long programId) {
        this.programId = programId;
    }
}
