package com.mice.backoffice.c2v_mice_backoffice_api.entity.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.CommunicationPayload;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class OpProgramSessionEntity {

    @Id
    @Column(name="row_num")
    private Integer rowNum;
    @Column(name="session_id", nullable = false)
    private Long sessionId;
    @Column(name="hall_name")
    private String hallName;
    @Column(name="session_name")
    private String sessionName;
    @Column(name="state_code", columnDefinition="0" )
    private Integer stateCode;
    @Column(name="online_code")
    private Integer onlineCode;
    @Column(name="session_type")
    private Integer sessionType;
    @Column(name="session_end_type")
    private Integer sessionEndType;
    @Column(name="question_yn")
    private String questionYn;
    @Column(name="mobile_chat_yn")
    private String mobileChatYn;
    @Column(name="session_start_timeline")
    private String sessionStartTimeline;
    @Column(name="session_end_timeline")
    private String sessionEndTimeline;
    @Column(name="min_diff")
    private Integer minDiff;
    @Column(name="max_member_count")
    private Integer maxMemberCount;
    @Transient
    private CommunicationPayload communication;
}
