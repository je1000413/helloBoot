package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class TicketUpdateRequest {
    private String ticketId;
    private TicketOnlineCode ticketOnlineCode;
    private long price;
    private int maxTicketCount;
    @NotNull(message = "ticketName is null")
    private Map<LanguageType, String> ticketName;

    public Map<LanguageType, String> getTicketName() {
        return Objects.isNull(ticketName) || ticketName.isEmpty() ? null : Collections.unmodifiableMap(ticketName);
    }
}
