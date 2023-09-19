package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import lombok.*;

@Data
@Builder
public class GroupServerRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Area {
        private String groupName;
        @Builder.Default
        private Integer serviceType = 3;
        private String areaName;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Stream {
        @Builder.Default
        private String cid = "c2v";
        private String streamPath;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Conference {
        private String serviceName;
    }

}
