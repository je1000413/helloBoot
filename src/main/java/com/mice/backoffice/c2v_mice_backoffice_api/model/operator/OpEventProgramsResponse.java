package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports;
import lombok.*;

import java.util.List;

@Data
@Builder
public class OpEventProgramsResponse {

    private Long eventID;

    private String eventName;

    private EventSupports.EventStateCode stateCode;

    private List<Program> programs;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Program {
        private Long programId;

        private String programName;

        private ProgramSupports.ProgramType programType;

        private ProgramSupports.SwitchType switchType;
    }
}
