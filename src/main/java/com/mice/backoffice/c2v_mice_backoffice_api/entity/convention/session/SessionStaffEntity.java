package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.StaffType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionStaffCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionStaffUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "session_staff")
@Getter
@SuperBuilder
public class SessionStaffEntity extends JpaBaseUserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "staff_id") long staffId;
    private @Column(name = "staff_name") String staffName;
    private @Column(name = "domain_name") String domainName;
    private @Column(name = "staff_description") String staffDescription;
    private @Column(name = "staff_type") StaffType staffType;
    private @Column(name = "photo_path") String photoPath;
    private @Column(name = "use_yn") char useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private SessionEntity session;

    public void update(SessionStaffUpdateRequest req) {
        this.staffType = req.getStaffType();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.useYn = 'n';
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static SessionStaffEntityBuilder dataBuilder(SessionStaffCreateRequest req) {
        return builder()
                .staffType(req.getStaffType())
                .useYn('y')
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }
}
