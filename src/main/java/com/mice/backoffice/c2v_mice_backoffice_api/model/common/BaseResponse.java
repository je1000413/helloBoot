package com.mice.backoffice.c2v_mice_backoffice_api.model.common;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder()
public class BaseResponse {
    private long accountId;
}
