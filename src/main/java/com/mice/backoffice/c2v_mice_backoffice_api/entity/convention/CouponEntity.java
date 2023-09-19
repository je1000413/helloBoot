package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.NoticeBoardUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coupon")
@Getter
@SuperBuilder
public class CouponEntity extends JpaBaseUserEntity {
    @Id
    private @Column(name = "coupon_no") String couponNo;
    private @Column(name = "package_id") String packageId;
    private @Column(name = "coupon_name") String couponName;
    private @Column(name = "price") long price;
    private @Column(name = "state_code") int stateCode;
    private @Column(name = "start_datetime") LocalDateTime startDatetime;
    private @Column(name = "end_datetime") LocalDateTime endDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity createUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity updateUser;

    public void update(NoticeBoardUpdateRequest req) {
        updateUserId(RequestUtils.getCurAdminId());
    }

}
