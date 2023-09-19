package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseTimeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenDisplayCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenDisplayUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "screen_display")
@Getter
@SuperBuilder
public class ScreenDisplayEntity extends JpaBaseUserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "display_seq") int displaySeq;
    private @Column(name = "page_no") int pageNo;
    private @Column(name = "display_type") DisplayType displayType;
    private @Column(name = "file_name") String fileName;
    private @Column(name = "display_contents") String displayContents;
    private @Column(name = "link_address") String linkAddress;
    private @Column(name = "state_code") ScreenDisplayStateCode stateCode;
    private @Column(name = "sound_yn") char soundYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private ScreenEntity screen;

    public void update(ScreenDisplayUpdateRequest req) {
        this.pageNo = req.getPageNo();
        this.displayType = req.getDisplayType();
        this.stateCode = req.getStateCode();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.stateCode = ScreenDisplayStateCode.DELETE;
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static ScreenDisplayEntityBuilder dataBuilder(ScreenDisplayCreateRequest req) {
        Supports.ScreenDisplayStateCode screenDisplayStateCode = (req.getStateCode() == null) ? Supports.ScreenDisplayStateCode.USE : req.getStateCode();

        return builder()
                .pageNo(req.getPageNo())
                .stateCode(screenDisplayStateCode)
                .soundYn('n')
                .displayType(req.getDisplayType())
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }

    public void updateLounge(int pageNo, DisplayType displayType) {
        this.pageNo = pageNo;
        this.displayType = displayType;
        updateUserId(RequestUtils.getCurAdminId());
    }

}
