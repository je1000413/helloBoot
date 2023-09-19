package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@Table(name = "banner_display")
@Getter
public class BannerDisplayEntity extends JpaBaseUserEntity {
    @Id
    private @Column(name = "display_seq") long displaySeq;
    private @Column(name = "banner_id") long bannerId;
    private @Column(name = "page_no") int pageNo;
    private @Column(name = "display_contents") String displayContents;
    private @Column(name = "link_address") String linkAddress;
    private @Column(name = "display_start_datetime") LocalDateTime displayStartDatetime;
    private @Column(name = "display_end_datetime") LocalDateTime displayEndDatetime;
    private @Column(name = "always_display_yn") char alwaysDisplayYn;
    private @Column(name = "state_code") int stateCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity createUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity updateUser;


    public void updateBannerDisplay(Supports.BannerDisplayStateCode stateCode, char alwaysDisplayYn, LocalDateTime displayStartDatetime, LocalDateTime displayEndDatetime) {
        this.stateCode = stateCode.getType();
        this. alwaysDisplayYn = alwaysDisplayYn;
        this.displayStartDatetime = displayStartDatetime;
        this.displayEndDatetime = displayEndDatetime;
        updateUserId(RequestUtils.getCurAdminId());
        forceUpdate();
    }
    public void deleteBannerDisplay(Supports.BannerDisplayStateCode delete) {
        this.stateCode = delete.getType();
    }
}
