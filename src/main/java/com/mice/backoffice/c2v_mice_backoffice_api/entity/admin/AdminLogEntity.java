package com.mice.backoffice.c2v_mice_backoffice_api.entity.admin;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports.AdminLogActionCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports.AdminLogMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.AdminLogCreateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "admin_log")
@Getter
public class AdminLogEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "log_id") long logId;
    private @Column(name = "mapping_id") String mappingId;
    private @Column(name = "mapping_type") AdminLogMappingType mappingType;
    private @Column(name = "action_code") AdminLogActionCode actionCode;
    private @Column(name = "message") String message;
    private @Column(name = "create_datetime") LocalDateTime createDatetime;
    private @Column(name = "admin_id") long adminId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", insertable = false, updatable = false)
    private AdminEntity admin;

    @Builder
    public AdminLogEntity(long logId, String mappingId, AdminLogMappingType mappingType, AdminLogActionCode actionCode, String message, LocalDateTime createDatetime, long adminId) {
        this.logId = logId;
        this.mappingId = mappingId;
        this.mappingType = mappingType;
        this.actionCode = actionCode;
        this.message = message;
        this.createDatetime = createDatetime;
        this.adminId = adminId;
    }

    public static AdminLogEntityBuilder dataBuilder(AdminLogCreateDto dto) {
        return builder()
                .mappingId(dto.getMappingId())
                .mappingType(dto.getMappingType())
                .actionCode(dto.getActionCode())
                .message(dto.getMessage())
                .createDatetime(LocalDateTime.now())
                .adminId(RequestUtils.getCurAdminId());
    }
}
