package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
@Table(name = "banner")
@Getter
public class BannerEntity extends JpaBaseUserEntity {

    @Id
    private @Column(name = "banner_id") long bannerId;
    private @Column(name = "banner_code") int bannerCode;
    private @Column(name = "location") String location;
    private @Column(name = "location_description") String locationDescription;
    private @Column(name = "size_x") BigDecimal sizeX;
    private @Column(name = "size_y") BigDecimal sizeY;
    private @Column(name = "state_code") Supports.BannerStateCode stateCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity createUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity updateUser;
}
