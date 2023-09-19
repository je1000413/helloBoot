package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OpSessionStaffsResponse {
    private Long sessionId;
    private String sessionName;
    private String sessionDescription;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;


    private List<OpSessionStaffsResponse.SessionStaff> sessionStaffs;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class SessionStaff {
        private Long staffId;
        private SessionSupports.StaffType staffType;
        private String staffName;
        private String staffDomainName;
        private String staffDescription;
        private String staffPhotoPath;
    }
}
