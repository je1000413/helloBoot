package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.TicketGetDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.PackageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.XPackageGetResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class PackageGetResponse {

    private String packageId;
    private PackageType packageType;
    private Map<LanguageType, String> packageName;
    private long createUserId;
    private long updateUserId;
    private TicketGetResponse ticket;

    @Builder
    public PackageGetResponse(String packageId, PackageType packageType, Map<LanguageType, String> packageName, long createUserId, long updateUserId, TicketGetResponse ticket) {
        this.packageId = packageId;
        this.packageType = packageType;
        this.packageName = packageName;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
        this.ticket = ticket;
    }

    public static PackageGetResponse makeGetResponse(TicketGetDto dto) {
        TicketGetResponse ticketGetResponse = TicketGetResponse.convertDtoToResponse(dto);

        return builder()
                .packageId(dto.getTicketId())
                .packageType(dto.getPackageType())
                .ticket(ticketGetResponse)
                .createUserId(dto.getCreateUserId())
                .updateUserId(dto.getUpdateUserId())
                .build();

    }

    public static PackageGetResponseBuilder dataBuilder(TicketGetDto dto) {
        return builder()
                .packageType(dto.getPackageType())
                .createUserId(dto.getCreateUserId())
                .updateUserId(dto.getUpdateUserId());
    }
}
