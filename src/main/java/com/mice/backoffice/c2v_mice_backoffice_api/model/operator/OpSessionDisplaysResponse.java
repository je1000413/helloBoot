package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.*;

import java.util.List;

@Data
@Builder
public class OpSessionDisplaysResponse {

    private Long screenId;

    //private String spaceId;

    //private String spaceObjectId;

    private List<SessionDisplay> sessionDisplays;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class SessionDisplay {
        private Integer displaySeq;

        private Integer pageNo;

        private Supports.DisplayCode displayCode;
        private Supports.DisplayType displayType;
        private String displayContents;
        private String linkAddress;
        private String soundYn;
    }

}




