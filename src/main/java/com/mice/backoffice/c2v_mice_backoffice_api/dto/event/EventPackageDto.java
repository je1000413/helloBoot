package com.mice.backoffice.c2v_mice_backoffice_api.dto.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageAuthorityCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage.EventPackageCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage.EventPackageUpdateRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EventPackageDto {
    private PackageType packageType;
    private PackageAuthorityCode packageAuthorityCode;

    private long price;
    private int maxPackageCount;
    private long eventId;
    private Long programId;
    private List<Long> sessionIds;

    private List<String> ticketIdList;

    @Builder
    public EventPackageDto(PackageType packageType, PackageAuthorityCode packageAuthorityCode, long price, int maxPackageCount, long eventId, Long programId, List<Long> sessionIds) {
        this.packageAuthorityCode = packageAuthorityCode;
        this.price = price;
        this.maxPackageCount = maxPackageCount;
        this.packageType = packageType;
        this.eventId = eventId;
        this.programId = programId;
        this.sessionIds = sessionIds;
    }

    public static EventPackageDto makeInstance(EventPackageCreateRequest req) {
        return builder()
                .packageType(req.getPackageType())
                .packageAuthorityCode(req.getPackageAuthorityCode())
                .price(req.getPrice())
                .maxPackageCount(req.getMaxPackageCount())
                .eventId(req.getEventId())
                .programId(req.getProgramId())
                .sessionIds(req.getSessionIds())
                .build();
    }

    public static EventPackageDto makeInstance(EventPackageUpdateRequest req) {
        return builder()
                .packageType(req.getPackageType())
                .packageAuthorityCode(req.getPackageAuthorityCode())
                .price(req.getPrice())
                .maxPackageCount(req.getMaxPackageCount())
                .eventId(req.getEventId())
                .programId(req.getProgramId())
                .sessionIds(req.getSessionIds())
                .build();
    }
}
