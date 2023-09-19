package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "session")
@Getter
@SuperBuilder
public class SessionEntity extends JpaBaseUserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "session_id") long sessionId;
    private @Column(name = "session_name") String sessionName;
    private @Column(name = "session_type") SessionType sessionType;
    private @Column(name = "session_description") String sessionDescription;
    private @Column(name = "session_end_type") SessionEndType sessionEndType;
    private @Column(name = "max_member_count") int maxMemberCount;
    private @Column(name = "hall_name") String hallName;
    private @Column(name = "space_id") String spaceId;
    private @Column(name = "template_id") long templateId;
    private @Column(name = "start_datetime") LocalDateTime startDatetime;
    private @Column(name = "end_datetime") LocalDateTime endDatetime;
    private @Column(name = "hall_start_datetime") LocalDateTime hallStartDatetime;
    private @Column(name = "hall_end_datetime") LocalDateTime hallEndDatetime;
    private @Column(name = "parent_session_id") long parentSessionId;
    private @Column(name = "state_code") SessionStateCode stateCode;
    private @Column(name = "online_code") SessionOnlineCode onlineCode;
    private @Column(name = "mobile_chat_yn") char mobileChatYn = 'y';
    private @Column(name = "question_yn") char questionYn = 'n';
    private @Column(name = "use_yn")  char useYn = 'y';

    // Program:Session 1:n
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private ProgramEntity program;

    @OneToMany(mappedBy = "ticketId.session")
    private List<TicketEntity> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "session")
    private List<SessionStaffEntity> sessionStaffs = new ArrayList<>();

    @OneToMany(mappedBy = "sessionDisplayId.session")
    private List<SessionDisplayEntity> sessionDisplays = new ArrayList<>();

    @OneToMany(mappedBy = "session")
    private List<SessionQuestionEntity> sessionQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "session")
    private List<ScreenEntity> screens = new ArrayList<>();

    public void update(SessionUpdateRequest req) {
        this.sessionType = req.getSessionType();
        this.maxMemberCount = req.getMaxMemberCount();
        this.templateId = req.getTemplateId();
        this.startDatetime = req.getStartDatetime();
        this.endDatetime = req.getEndDatetime();
        this.hallStartDatetime = req.getHallStartDatetime();
        this.hallEndDatetime = req.getHallEndDatetime();
        this.parentSessionId = req.getParentSessionId();
        this.questionYn = req.getQuestionYn();
        this.stateCode = req.getStateCode();
        this.onlineCode = req.getOnlineCode();
        this.mobileChatYn = req.getMobileChatYn();

        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.useYn = 'n';
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static SessionEntityBuilder dataBuilder(@NotNull SessionCreateRequest req) {
        return builder()
                .sessionType(req.getSessionType())
                .sessionEndType(SessionEndType.FORCED_EXIT)
                .maxMemberCount(req.getMaxMemberCount())
                .templateId(req.getTemplateId())
                .startDatetime(req.getStartDatetime())
                .endDatetime(req.getEndDatetime())
                .hallStartDatetime(req.getHallStartDatetime())
                .hallEndDatetime(req.getHallEndDatetime())
                .stateCode(req.getStateCode())
                .onlineCode(req.getOnlineCode())
                .parentSessionId(req.getParentSessionId())
                .mobileChatYn(req.getMobileChatYn())
                .useYn('y')
                .questionYn(req.getQuestionYn())
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }
}