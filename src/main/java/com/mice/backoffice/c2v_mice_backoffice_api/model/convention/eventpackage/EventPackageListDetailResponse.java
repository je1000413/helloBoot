package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageAuthorityCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.event.EventPackageListDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventPackageListDetailResponse {
    private long itemNo;
    private String packageId;
    private TicketOnlineCode onlineCode;
    private String packageName;
    private PackageType packageType;
    private PackageAuthorityCode authorityCode;
    private String eventName;
    private String programName;
    private String sessionName;
    private long price;
    private boolean isAuto;
    private int maxPackageCount;
    private PackageStateCode stateCode;
    private LocalDateTime createDateTime;

    @Builder
    public EventPackageListDetailResponse(long itemNo, String packageId, TicketOnlineCode onlineCode, String packageName, PackageType packageType, PackageAuthorityCode authorityCode,
                                          String eventName, String programName, String sessionName, long price, boolean isAuto, int maxPackageCount, PackageStateCode stateCode, LocalDateTime createDateTime) {
        this.itemNo = itemNo;
        this.packageId = packageId;
        this.onlineCode = onlineCode;
        this.packageName = packageName;
        this.packageType = packageType;
        this.authorityCode = authorityCode;
        this.eventName = eventName;
        this.programName = programName;
        this.sessionName = sessionName;
        this.price = price;
        this.isAuto = isAuto;
        this.maxPackageCount = maxPackageCount;
        this.stateCode = stateCode;
        this.createDateTime = createDateTime;
    }

    public static EventPackageListDetailResponseBuilder dataBuilder(EventPackageListDto dto) {
        // tc prefix로 시작하는 key는 세션에서 자동 생성 된 값이다.
        boolean isAuto = dto.getPackageId().toLowerCase().startsWith("t");

        return builder()
                .packageId(dto.getPackageId())
                .onlineCode(dto.getOnlineCode())
                .eventName(dto.getEventName())
                .packageName(dto.getPackageName())
                .packageType(dto.getPackageType())
                .authorityCode(dto.getAuthorityCode())
                .price(dto.getPrice())
                .isAuto(isAuto)
                .maxPackageCount(dto.getMaxPackageCount())
                .stateCode(dto.getStateCode())
                .createDateTime(dto.getCreateDateTime());

    }
}
