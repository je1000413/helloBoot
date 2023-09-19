package com.mice.backoffice.c2v_mice_backoffice_api.slave.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mice.backoffice.c2v_mice_backoffice_api.common.MapToJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CouponListLogEntity {

    @Column(name = "totalRows")
    private Integer totalRows;

    @Id
    @Column(name = "row_num")
    @JsonProperty("row_num")
    private Integer rowNum;

    @Column(name = "packageId")
    private String packageId;

    @Column(name = "packageName")
    private String packageName;

    @Column(name="eventName")
    private String eventName;

    @Column(name="programNames")
    private String programNames;

    @Column(name="sessionNames")
    private String sessionNames;

    @Column(name="staffType")
    private String staffType;

    @Column(name = "issueMethod")
    private String issueMethod;

    @Column(name = "successCouponCnt")
    private int successCouponCnt;

    @Column(name = "failCouponCnt")
    private int failCouponCnt;

    @Column(name = "issueUser")
    private String issueUser;

    @Column(name = "createDateTime")
    private String createDateTime;
}
