package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
public class GroupServerResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Area {
        @JsonProperty("GroupId")
        private String groupId;
        @JsonProperty("GroupName")
        private String groupName;
        @JsonProperty("AreaName")
        private String areaName;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Stream {
        private String streamKey;
        private String ingestUrl;
        private String streamingUrl;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Conference {
        private String serviceName;
        private String roomId;
        private String mediaId;
        private String mediaUrl;
    }

}
