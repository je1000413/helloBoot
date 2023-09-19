package com.mice.backoffice.c2v_mice_backoffice_api.model.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CodeInfo {
    @JsonIgnore
    private String cdTyp;
    private  int cd;
    private String cdNm;
    private String cdDesc;
    private String useYn;
}
