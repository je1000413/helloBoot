package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports.ProgramType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports.SwitchType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseTimeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program.ProgramCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program.ProgramUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "program")
@Getter
@SuperBuilder
public class ProgramEntity extends JpaBaseUserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "program_id") long programId;
    private @Column(name = "program_name") String programName;
    private @Column(name = "program_description") String programDescription;
    private @Column(name = "program_type") ProgramType programType;
    private @Column(name = "switch_type") SwitchType switchType;
    private @Column(name = "use_yn") char useYn;

    // Event:Program 1:n
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @OneToMany(mappedBy = "program")
    private List<SessionEntity> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "program")
    private List<ScreenEntity> screens = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity createUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity updateUser;

    public void update(ProgramUpdateRequest req, EventEntity ee) {
        this.event = ee;
        this.programType = req.getProgramType();
        this.switchType = req.getSwitchType();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.useYn = 'n';
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static ProgramEntityBuilder dataBuilder(ProgramCreateRequest req) {
        return builder()
                .programType(req.getProgramType())
                .switchType(req.getSwitchType())
                .useYn('y')
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }
}
