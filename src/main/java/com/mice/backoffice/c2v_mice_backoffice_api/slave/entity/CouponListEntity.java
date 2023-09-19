package com.mice.backoffice.c2v_mice_backoffice_api.slave.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mice.backoffice.c2v_mice_backoffice_api.common.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
public class CouponListEntity {

    @Column(name = "totalRows")
    @JsonProperty("totalRows")
    private Integer totalRows;

    @Column(name = "row_num")
    @JsonProperty("row_num")
    private Integer rowNum;

    @Id
    @Column(name = "package_id")
    @JsonProperty("package_id")
    private String packageId;

    @Column(name = "package_name")
    @JsonProperty("package_name")
    private String packageName;

    @Column(name="event_name")
    @JsonProperty("event_name")
    private String eventName;

    @Column(name="program_names")
    @JsonProperty("program_names")
    private String programNames;

    @Column(name="session_names")
    @JsonProperty("session_names")
    private String sessionNames;

    @Column(name="staff_type")
    @JsonProperty("staff_type")
    private String staffType;

    @Column(name = "coupon_cnt")
    @JsonProperty("coupon_cnt")
    private int couponCnt;

    @Column(name = "issue")
    @JsonProperty("issue")
    private String issue;

    @Column(name = "create_datetime")
    @JsonProperty("create_datetime")
    private String createDateTime;
}
