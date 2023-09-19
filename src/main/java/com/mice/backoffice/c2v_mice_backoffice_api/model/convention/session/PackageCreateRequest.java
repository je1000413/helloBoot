package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class PackageCreateRequest {
    private Map<LanguageType, String> packageName;
    @NotNull(message = "ticket is null")
    private TicketCreateRequest ticket;

    @Builder
    public PackageCreateRequest(TicketCreateRequest ticket, Map<LanguageType, String> packageName) {
        this.ticket = ticket;
        this.packageName = packageName;
    }

    public Map<LanguageType, String> getPackageName() {
        return Objects.isNull(packageName) || packageName.isEmpty() ? null : Collections.unmodifiableMap(packageName);
    }

    public static PackageCreateRequest makeCreateRequest(PackageUpdateRequest req) {
        return builder()
                .ticket(TicketCreateRequest.makeCreateRequest(req.getTicket()))
                .packageName(req.getPackageName())
                .build();
    }
}
