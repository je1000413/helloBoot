package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class TicketCreateRequest {
    private TicketOnlineCode ticketOnlineCode;
    private long price;
    private int maxTicketCount;
    @NotNull(message = "ticketName is null")
    private Map<LanguageType, String> ticketName;

    @Builder
    public TicketCreateRequest(TicketOnlineCode ticketOnlineCode, long price, int maxTicketCount, Map<LanguageType, String> ticketName) {
        this.ticketOnlineCode = ticketOnlineCode;
        this.price = price;
        this.maxTicketCount = maxTicketCount;
        this.ticketName = ticketName;
    }

    public Map<LanguageType, String> getTicketName() {
        return Objects.isNull(ticketName) || ticketName.isEmpty() ? null : Collections.unmodifiableMap(ticketName);
    }

    public static TicketCreateRequest makeCreateRequest(TicketUpdateRequest req) {
        return builder()
                .ticketOnlineCode(req.getTicketOnlineCode())
                .price(req.getPrice())
                .maxTicketCount(req.getMaxTicketCount())
                .ticketName(req.getTicketName())
                .build();

    }
}
