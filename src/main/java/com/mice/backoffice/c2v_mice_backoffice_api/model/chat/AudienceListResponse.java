package com.mice.backoffice.c2v_mice_backoffice_api.model.chat;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.AudienceDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AudienceListResponse {
    private long totalCount;
    private List<AudienceInfo> visitors;

    @Builder
    public AudienceListResponse(long totalCount, List<AudienceInfo> visitors) {
        this.totalCount = totalCount;
        this.visitors = visitors;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AudienceInfo {
        private String userName;
        private String departmentName;
        private String photoUrl;

        @Builder
        public AudienceInfo(String userName, String departmentName, String photoUrl) {
            this.userName = userName;
            this.departmentName = departmentName;
            this.photoUrl = photoUrl;
        }

        public static AudienceInfo makeInstance(AudienceDto dto) {
            return  builder()
                    .userName(dto.getUserName())
                    .departmentName(dto.getDepartmentName())
                    .build();
        }
    }
}
