package com.mice.backoffice.c2v_mice_backoffice_api.model.admin;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DomainRequest {
    private String domainName;
    private String useYn;
}
