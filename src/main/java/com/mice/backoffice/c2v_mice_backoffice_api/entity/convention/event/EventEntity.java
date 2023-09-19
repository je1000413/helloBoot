package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.*;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseUserEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.PackageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "event")
@Setter
@Getter
@SuperBuilder
public class EventEntity extends JpaBaseUserEntity {
    @Id
    private @Column(name = "event_id") long eventId;
    private @Column(name = "event_name") String eventName;
    private @Column(name = "event_description") String eventDescription;
    private @Column(name = "offline_location") String offlineLocation;
    private @Column(name = "offline_detail_location") String offlineDetailLocation;
    private @Column(name = "start_datetime") LocalDateTime startDatetime;
    private @Column(name = "end_datetime") LocalDateTime endDatetime;
    private @Column(name = "sell_start_datetime") LocalDateTime sellStartDatetime;
    private @Column(name = "sell_end_datetime") LocalDateTime sellEndDatetime;
    private @Column(name = "display_start_datetime") LocalDateTime displayStartDatetime;
    private @Column(name = "display_end_datetime") LocalDateTime displayEndDatetime;
    private @Column(name = "lounge_start_datetime") LocalDateTime loungeStartDatetime;
    private @Column(name = "lounge_enter_datetime") LocalDateTime loungeEnterDatetime;
    private @Column(name = "lounge_end_datetime") LocalDateTime loungeEndDatetime;
    private @Column(name = "ticket_end_type") TicketEndType ticketEndType;
    private @Column(name = "state_code") EventStateCode stateCode;
    private @Column(name = "display_state_code") EventDisplayStateCode displayStateCode;
    private @Column(name = "sell_state_code") EventSellStateCode sellStateCode;
    private @Column(name = "space_state_code") EventSpaceStateCode eventSpaceStateCode;

    @OneToMany(mappedBy = "event")
    private List<ProgramEntity> programs = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<EventHostEntity> eventHosts = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<ScreenEntity> screens = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<PackageEntity> packages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity createUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id", referencedColumnName = "admin_id", insertable = false, updatable = false)
    private AdminEntity updateUser;

    public void update(EventUpdateRequest req) {
        this.startDatetime = req.getStartDatetime();
        this.endDatetime = req.getEndDatetime();
        this.sellStartDatetime = req.getSellStartDatetime();
        this.sellEndDatetime = req.getSellEndDatetime();
        this.displayStartDatetime = req.getDisplayStartDatetime();
        this.displayEndDatetime = req.getDisplayEndDatetime();
        this.ticketEndType = req.getTicketEndType();
        forceUpdate();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void updateDisplayStateCode(EventPatchMobileDisplayStatusRequest req) {
        this.displayStateCode = req.getMobileDisplayStatus();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void updateEmergencyTicketStateCode(EventPatchTicketStatusRequest req) {
        this.sellStateCode = req.getSellStateCode();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void update(EventShiftUpdateRequest req) {
        this.startDatetime = req.getStartDatetime();
        this.endDatetime = req.getEndDatetime();
        this.sellStartDatetime = req.getSellStartDatetime();
        this.sellEndDatetime = req.getSellEndDatetime();
        this.displayStartDatetime = req.getDisplayStartDatetime();
        this.displayEndDatetime = req.getDisplayEndDatetime();
        this.ticketEndType = req.getTicketEndType();
        this.stateCode = req.getStateCode();
        forceUpdate();
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void migration() {
        this.stateCode = EventStateCode.OPENED;
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void delete() {
        this.stateCode = EventStateCode.DELETE;
        updateUserId(RequestUtils.getCurAdminId());
    }

    public static EventEntityBuilder dataBuilder(@NotNull EventCreateRequest req) {
        return builder()
                .startDatetime(req.getStartDatetime())
                .endDatetime(req.getEndDatetime())
                .sellStartDatetime(req.getSellStartDatetime())
                .sellEndDatetime(req.getSellEndDatetime())
                .displayStartDatetime(req.getDisplayStartDatetime())
                .displayEndDatetime(req.getDisplayEndDatetime())
                .loungeStartDatetime(null)
                .loungeEnterDatetime(null)
                .loungeEndDatetime(null)
                .ticketEndType(req.getTicketEndType())
                .displayStateCode(EventDisplayStateCode.OPEN)
                .sellStateCode(EventSellStateCode.OPEN)
                .stateCode(EventStateCode.OPENED)
                .eventSpaceStateCode(EventSpaceStateCode.UNOPENED)
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }

    public static EventEntityBuilder dataBuilder(@NotNull EventShiftCreateRequest req) {
        LocalDateTime sellStartDateTime = (req.getSellStartDatetime() == null) ? req.getStartDatetime().minusMonths(1) : req.getSellStartDatetime();
        LocalDateTime sellEndDateTime = (req.getSellEndDatetime() == null) ? req.getEndDatetime().minusMonths(1) : req.getSellEndDatetime();

        return builder()
                .startDatetime(req.getStartDatetime())
                .endDatetime(req.getEndDatetime())
                .sellStartDatetime(sellStartDateTime)
                .sellEndDatetime(sellEndDateTime)
                .displayStartDatetime(req.getDisplayStartDatetime())
                .displayEndDatetime(req.getDisplayEndDatetime())
                .loungeStartDatetime(null)
                .loungeEnterDatetime(null)
                .loungeEndDatetime(null)
                .ticketEndType(req.getTicketEndType())
                .displayStateCode(EventDisplayStateCode.OPEN)
                .sellStateCode(EventSellStateCode.OPEN)
                .stateCode(EventStateCode.TEMP_SAVE)
                .eventSpaceStateCode(EventSpaceStateCode.UNOPENED)
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId());
    }

    public void lounge(LocalDateTime loungeStartDatetime, LocalDateTime loungeEndDatetime) {
        this.loungeStartDatetime = loungeStartDatetime;
        this.loungeEnterDatetime = loungeStartDatetime;
        this.loungeEndDatetime = loungeEndDatetime;
        updateUserId(RequestUtils.getCurAdminId());
    }

    public void updateLounge(LocalDateTime loungeStartDatetime, LocalDateTime loungeEndDatetime) {
        this.loungeStartDatetime = loungeStartDatetime;
        this.loungeEnterDatetime = loungeStartDatetime;
        this.loungeEndDatetime = loungeEndDatetime;
        forceUpdate();
        updateUserId(RequestUtils.getCurAdminId());
    }
}