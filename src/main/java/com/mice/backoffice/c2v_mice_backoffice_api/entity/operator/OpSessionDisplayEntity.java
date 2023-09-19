package com.mice.backoffice.c2v_mice_backoffice_api.entity.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class OpSessionDisplayEntity {

    @Id
    @Column(name="display_seq")
    private Integer displaySeq;

    @Column(name="screen_id")
    private Long ScreenId;

    @Column(name="session_id")
    private Long sessionId;

    @Column(name="page_no")
    private Integer pageNo;

    @Column(name="space_id")
    private String spaceId;

    @Column(name="space_object_id")
    private String spaceObjectId;

    @Column(name="display_code")
    private Supports.DisplayCode displayCode;

    @Column(name="display_type")
    private Supports.DisplayType displayType;

    @Column(name="display_contents")
    private String displayContents;

    @Column(name="link_address")
    private String linkAddress;

    @Column(name="sound_yn")
    private String soundYn;
}
