package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "screen")
@Getter
@SuperBuilder
public class ScreenEntity extends JpaBaseUserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "screen_id") long screenId;
    private @Column(name = "mapping_id") long mappingId;
    private @Column(name = "mapping_type") ScreenMappingType mappingType;
    private @Column(name = "space_id") String spaceId;
    private @Column(name = "space_object_id") long spaceObjectId;
    private @Column(name = "display_code") DisplayCode displayCode;
    private @Column(name = "state_code") ScreenStateCode stateCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mapping_id", referencedColumnName = "event_id", insertable = false, updatable = false)
    private EventEntity event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mapping_id", referencedColumnName = "program_id", insertable = false, updatable = false)
    private ProgramEntity program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mapping_id", referencedColumnName = "session_id", insertable = false, updatable = false)
    private SessionEntity session;

    @OneToMany(mappedBy = "screen")
    private List<ScreenDisplayEntity> screenDisplays = new ArrayList<>();

    public void update(ScreenUpdateRequest req) {
        this.spaceObjectId = req.getSpaceObjectId();
        this.displayCode = req.getDisplayCode();
        this.stateCode = req.getStateCode();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.stateCode = ScreenStateCode.DELETE;
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static ScreenEntityBuilder dataBuilder(ScreenCreateRequest req) {
        ScreenStateCode screenStateCode = (req.getStateCode() == null) ? ScreenStateCode.USE : req.getStateCode();

        return builder()
                .spaceObjectId(req.getSpaceObjectId())
                .displayCode(req.getDisplayCode())
                .stateCode(screenStateCode)
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }
}
