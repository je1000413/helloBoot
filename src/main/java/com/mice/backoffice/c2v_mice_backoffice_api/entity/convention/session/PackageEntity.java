package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageAuthorityCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.event.EventPackageDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage.EventPackageCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "package")
@Getter
@SuperBuilder
public class PackageEntity extends JpaBaseUserEntity {
    @Id
    private @Column(name = "package_id") String packageId;
    private @Column(name = "event_id") long eventId;
    private @Column(name = "package_name") String packageName;
    private @Column(name = "package_type") PackageType packageType;
    private @Column(name = "ticket_ids") String ticketIds;
    private @Column(name = "max_package_count") int maxPackageCount;
    private @Column(name = "price") long price;
    private @Column(name = "authority_code") PackageAuthorityCode packageAuthorityCode;
    private @Column(name = "state_code") PackageStateCode stateCode;
    private @Column(name = "use_yn") char useYn;
    private @Column(name = "payment_path") String paymentPath;
    private @Column(name = "start_datetime") LocalDateTime startDatetime;
    private @Column(name = "end_datetime") LocalDateTime endDatetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", insertable = false, updatable = false)
    private EventEntity event;

    public void update(TicketUpdateRequest tReq) {
        this.maxPackageCount = tReq.getMaxTicketCount();
        this.price = tReq.getPrice();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void update(EventPackageDto req) {
        this.packageAuthorityCode = req.getPackageAuthorityCode();
        this.packageType = req.getPackageType();
        this.price = req.getPrice();
        this.maxPackageCount = req.getMaxPackageCount();
        this.eventId = req.getEventId();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void updateTicketIds(String ticketIds) {
        this.ticketIds = ticketIds;
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void changePackageStateCode(PackageStateCode stateCode) {
        this.stateCode = stateCode;
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.useYn = 'n';
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static PackageEntityBuilder dataBuilder(TicketCreateRequest tcr) {
        return builder()
                .packageType(PackageType.SELECT)
                .maxPackageCount(tcr.getMaxTicketCount())
                .price(tcr.getPrice())
                .packageAuthorityCode(PackageAuthorityCode.VISITOR)
                .stateCode(PackageStateCode.SALE)
                .useYn('y')
                .paymentPath("")
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());

    }

    public static PackageEntityBuilder dataBuilder(EventPackageCreateRequest req) {
        return builder()
                .packageType(req.getPackageType())
                .maxPackageCount(req.getMaxPackageCount())
                .price(req.getPrice())
                .packageAuthorityCode(req.getPackageAuthorityCode())
                .stateCode(PackageStateCode.SALE)
                .useYn('y')
                .eventId(req.getEventId())
                .paymentPath("")
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }


}
