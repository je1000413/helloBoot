package com.mice.backoffice.c2v_mice_backoffice_api.model.operator;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpAdminListSqlRequest {
    @Column(name = "in_operator_name")
    private String operatorName;
    @Column(name = "in_operator_id")
    private String operatorId;
    @Column(name = "in_staff_type")
    private long staffType;
    @Column(name = "in_page_size")
    private int pageSize;
    @Column(name = "in_page_num")
    private int pageNum;
}