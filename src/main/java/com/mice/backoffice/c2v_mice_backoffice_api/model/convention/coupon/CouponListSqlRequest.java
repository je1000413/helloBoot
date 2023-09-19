package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.coupon;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CouponListSqlRequest {
    @Column(name = "in_start_create_datetime")
    private String startCreateDatetime;
    @Column(name = "in_end_create_datetime")
    private String endCreateDatetime;
    @Column(name = "in_start_event_datetime")
    private String startEventDatetime;
    @Column(name = "in_end_event_datetime")
    private String endEventDatetime;
    @Column(name = "in_start_update_datetime")
    private String startUpdateDatetime;
    @Column(name = "in_end_update_datetime")
    private String endUpdateDatetime;
    @Column(name = "in_state_code")
    private String stateCode;
    @Column(name = "in_staff_type")
    private String staffType;
    @Column(name = "in_package_name")
    private String packageName;
    @Column(name = "in_package_id")
    private String packageId;
    @Column(name = "in_event_name")
    private String eventName;
    @Column(name = "in_event_id")
    private String eventId;
    @Column(name = "in_company_name")
    private String companyName;
    @Column(name = "in_company_id")
    private String companyId;
    @Column(name = "in_page_size")
    private int pageSize;
    @Column(name = "in_page_num")
    private int pageNum;

    @Override
    public String toString() {
        return "CouponListSqlRequest{" +
                "startCreateDatetime=" + startCreateDatetime +  '\'' +
                ", endCreateDatetime='" + endCreateDatetime + '\'' +
                ", startEventDatetime='" + startEventDatetime + '\'' +
                ", endEventeDatetime='" + endEventDatetime + '\'' +
                ", startUpdateDatetime='" + startUpdateDatetime + '\'' +
                ", endUpdateDatetime='" + endUpdateDatetime + '\'' +
                ", stateCode='" + stateCode + '\'' +
                ", staffType='" + staffType + '\'' +
                ", packageName='" + packageName + '\'' +
                ", packageId='" + packageId + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventId='" + eventId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", pageNum='" + pageNum +
                '}';
    }
}