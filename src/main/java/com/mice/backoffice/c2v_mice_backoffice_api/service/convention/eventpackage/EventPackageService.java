package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.eventpackage;

import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage.*;

public interface EventPackageService {
    EventPackageListResponse list(EventPackageQueryParam cond) throws Exception;
    EventPackageGetResponse get(String packageId) throws Exception;
    EventPackageCreateResponse create(EventPackageCreateRequest req) throws Exception;
    void update(String packageId, EventPackageUpdateRequest req) throws Exception;
    void changePackageType(String packageId, EventPackagePatchRequest req) throws Exception;
    void delete(String packageId) throws Exception;
}
