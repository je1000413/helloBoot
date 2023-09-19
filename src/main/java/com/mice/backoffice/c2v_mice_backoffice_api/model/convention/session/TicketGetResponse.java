package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackagePriceType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.TicketGetDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.TicketEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TicketGetResponse {
    private String ticketId;
    private TicketOnlineCode ticketOnlineCode;
    private long price;
    private int maxTicketCount;
    private Map<LanguageType, String> ticketName;
    private long createUserId;
    private long updateUserId;

    // 기획 변경으로 인한 사용 안함.
    // 기존 레거시 코드 유지를 위해 삭제는 하지 않고, 노출 하지 않도록 예외처리
    @JsonIgnore
    private PackagePriceType packagePriceType;

    @Builder
    public TicketGetResponse(String ticketId, TicketOnlineCode ticketOnlineCode, long price, int maxTicketCount, Map<LanguageType, String> ticketName, PackagePriceType packagePriceType, long createUserId, long updateUserId) {
        this.ticketId = ticketId;
        this.ticketOnlineCode = ticketOnlineCode;
        this.price = price;
        this.maxTicketCount = maxTicketCount;
        this.ticketName = ticketName;
        this.packagePriceType = packagePriceType;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
    }


    public static TicketGetResponseBuilder dataBuilder(TicketEntity te) {
        return builder()
                .ticketId(te.getTicketId().getTicketId())
                .ticketOnlineCode(te.getTicketOnlineCode())
                .createUserId(te.getCreateUserId())
                .updateUserId(te.getUpdateUserId());

    }

    public static TicketGetResponse convertDtoToResponse(TicketGetDto dto) {
        return builder()
                .ticketId(dto.getTicketId())
                .ticketOnlineCode(dto.getTicketOnlineCode())
                .price(dto.getPrice())
                .maxTicketCount(dto.getMaxPackageCount())
                .ticketName(dto.getTicketName())
                .createUserId(dto.getCreateUserId())
                .updateUserId(dto.getUpdateUserId())
                .build();
    }
}
