package com.mice.backoffice.c2v_mice_backoffice_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TicketGetDto {
    private String ticketId;
    @JsonIgnore
    private String ticketNameKey;
    private String packageNameKey;
    private long price;
    private TicketOnlineCode ticketOnlineCode;
    private PackageType packageType;
    private int maxPackageCount;
    private Map<LanguageType, String> ticketName;
    private long createUserId;
    private long updateUserId;

    @QueryProjection
    public TicketGetDto(String ticketId, String ticketNameKey, String packageNameKey, long price, TicketOnlineCode ticketOnlineCode,
                        int maxPackageCount, PackageType packageType, long createUserId, long updateUserId) {
        this.ticketId = ticketId;
        this.ticketNameKey = ticketNameKey;
        this.packageNameKey = packageNameKey;
        this.price = price;
        this.ticketOnlineCode = ticketOnlineCode;
        this.maxPackageCount = maxPackageCount;
        this.ticketName = null;
        this.packageType = packageType;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
    }
}
