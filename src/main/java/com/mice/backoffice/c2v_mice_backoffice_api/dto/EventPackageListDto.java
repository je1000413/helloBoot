package com.mice.backoffice.c2v_mice_backoffice_api.dto;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageAuthorityCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class EventPackageListDto {
    private long itemNo;
    private String packageId;
    private TicketOnlineCode onlineCode;
    private String packageName;
    private PackageType packageType;
    private PackageAuthorityCode authorityCode;
    private EventEntity event;
    private long price;
    private int maxPackageCount;
    private String ticketIds;
    private PackageStateCode stateCode;
    private LocalDateTime createDateTime;

    @QueryProjection
    public EventPackageListDto(String packageId, TicketOnlineCode onlineCode, String packageName, PackageType packageType, PackageAuthorityCode authorityCode,
                               EventEntity event, long price, int maxPackageCount, String ticketIds, PackageStateCode stateCode, LocalDateTime createDateTime) {
        this.packageId = packageId;
        this.onlineCode = onlineCode;
        this.packageName = packageName;
        this.packageType = packageType;
        this.authorityCode = authorityCode;
        this.event = event;
        this.price = price;
        this.maxPackageCount = maxPackageCount;
        this.ticketIds = ticketIds;
        this.stateCode = stateCode;
        this.createDateTime = createDateTime;
    }
}
