package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.PackageGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.PackageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.TicketEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.PackageCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.XPackageGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.PackageUpdateRequest;

import java.util.List;

public interface PackageService {
    public void create(List<PackageCreateRequest> list, SessionEntity se, long eventId);
    public XPackageGetResponse get(List<PackageEntity> packageList, List<TicketEntity> ticketList);
    public List<PackageGetResponse> list(long sessionId);
    public void delete(PackageUpdateRequest packageInfo, SessionEntity se, long eventId);
    public void update(List<PackageUpdateRequest> packages, SessionEntity se, long eventId);
}
