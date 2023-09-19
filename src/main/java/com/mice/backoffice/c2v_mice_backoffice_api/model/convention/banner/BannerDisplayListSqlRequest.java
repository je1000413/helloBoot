package com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BannerDisplayListSqlRequest {

    @Column(name = "in_start_create_datetime")
    private String startCreateDatetime;
    @Column(name = "in_end_create_datetime")
    private String endCreateDatetime;
    @Column(name = "in_start_update_datetime")
    private String startUpdateDatetime;
    @Column(name = "in_end_update_datetime")
    private String endUpdateDatetime;
    @Column(name = "in_banner_code")
    private String bannerCode;
    @Column(name = "in_banner_display_state_code")
    private String bannerDisplayStateCode;
    @Column(name = "in_location_description")
    private String locationDescription;
    @Column(name = "in_location")
    private String location;
    @Column(name = "in_update_user")
    private String updateUser;
    @Column(name = "in_update_user_id")
    private String updateUserId;
    @Column(name = "in_page_size")
    private int pageSize;
    @Column(name = "in_page_num")
    private int pageNum;

    @Override
    public String toString() {
        return "CouponListSqlRequest{" +
                "startCreateDatetime=" + startCreateDatetime +  '\'' +
                ", endCreateDatetime='" + endCreateDatetime + '\'' +
                ", startUpdateDatetime='" + startUpdateDatetime + '\'' +
                ", endUpdateDatetime='" + endUpdateDatetime + '\'' +
                ", bannerCode='" + bannerCode + '\'' +
                ", bannerDisplayStateCode='" + bannerDisplayStateCode + '\'' +
                ", locationDescription='" + locationDescription + '\'' +
                ", location='" + location + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", pageNum='" + pageNum +
                '}';
    }
}
