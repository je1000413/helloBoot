package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge;

import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoungeListSqlRequest {
    @Column(name = "in_start_create_datetime")
    private String startCreateDatetime;
    @Column(name = "in_end_create_datetime")
    private String endCreateDatetime;
    @Column(name = "in_start_lounge_datetime")
    private String startLoungeDatetime;
    @Column(name = "in_end_lounge_datetime")
    private String endLoungeDatetime;
    @Column(name = "in_start_update_datetime")
    private String startUpdateDatetime;
    @Column(name = "in_end_update_datetime")
    private String endUpdateDatetime;
    @Column(name = "in_template_id")
    private Long templateId;
    @Column(name = "in_event_name")
    private String eventName;
    @Column(name = "in_event_id")
    private String eventId;
    @Column(name = "in_lounge_name")
    private String loungeName;
    @Column(name = "in_lounge_no")
    private String loungeNo;
    @Column(name = "in_domain_name")
    private String domainName;
    @Column(name = "in_domain_id")
    private String domainId;
    @Column(name = "in_page_size")
    private int pageSize;
    @Column(name = "in_page_num")
    private int pageNum;
}