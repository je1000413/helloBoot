package com.mice.backoffice.c2v_mice_backoffice_api.entity.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class OpSessionDetailEntity {

    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "session_name")
    private String sessionName;

    @Column(name = "session_description")
    private String sessionDescription;

    @Column(name = "start_datetime")
    private LocalDateTime startDatetime;

    @Column(name = "end_datetime")
    private LocalDateTime endDatetime;

    @Id
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "staff_type")
    private SessionSupports.StaffType staffType;

    @Column(name = "staff_name")
    private String staffName;

    @Column(name = "staff_domain_name")
    private String staffDomainName;

    @Column(name = "staff_description")
    private String staffDescription;

    @Column(name = "staff_photo_path")
    private String staffPhotoPath;
}
