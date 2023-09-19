package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import lombok.Data;

@Data
public class OpSessionOperatorRequest {
    private String source;
    private SessionSupports.SessionType sourceType;
}
