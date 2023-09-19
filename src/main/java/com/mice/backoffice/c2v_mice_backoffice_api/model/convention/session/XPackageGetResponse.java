package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.PackageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class XPackageGetResponse {
    private PackageType packageType;
    private Map<LanguageType, String> packageName;
    private long createUserId;
    private long updateUserId;

    private List<TicketGetResponse> tickets;
    private List<String> packageIds;

    @Builder
    public XPackageGetResponse(PackageType packageType, Map<LanguageType, String> packageName, long createUserId, long updateUserId, List<TicketGetResponse> tickets, List<String> packageIds) {
        this.packageType = packageType;
        this.packageName = packageName;
        this.tickets = tickets;
        this.packageIds = packageIds;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
    }

    public void addTicket(TicketGetResponse ticketGetResponse) {
        if (Objects.isNull(tickets))
            tickets = new ArrayList<>();

        tickets.add(ticketGetResponse);
    }

    public void addPackageId(String packageId) {
        if (Objects.isNull(packageIds))
            packageIds = new ArrayList<>();

        packageIds.add(packageId);
    }

    public static XPackageGetResponseBuilder dataBuilder(PackageEntity pe) {
        return builder()
                .packageType(pe.getPackageType())
                .createUserId(pe.getCreateUserId())
                .updateUserId(pe.getUpdateUserId());
    }
}
