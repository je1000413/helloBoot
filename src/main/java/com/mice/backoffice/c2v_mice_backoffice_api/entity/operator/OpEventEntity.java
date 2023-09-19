package com.mice.backoffice.c2v_mice_backoffice_api.entity.operator;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class OpEventEntity
{
    @Column(name="event_id")
    private Long eventId;

    @Column(name="event_name")
    private String eventName;

    @Column(name="state_code")
    private EventSupports.EventStateCode stateCode;

    @Column(name="state_name")
    private String stateName;

    @Id
    @Column(name="program_id")
    private Long programId;

    @Column(name="program_name")
    private String programName;

    @Column(name="program_type")
    private ProgramSupports.ProgramType programType;

    @Column(name="switch_type")
    private ProgramSupports.SwitchType switchType;

}
