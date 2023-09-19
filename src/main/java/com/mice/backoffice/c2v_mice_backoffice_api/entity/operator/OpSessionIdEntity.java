package com.mice.backoffice.c2v_mice_backoffice_api.entity.operator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class OpSessionIdEntity {
    @Column(name="event_id")
    private Long eventId;

    @Column(name="program_id")
    private Long programId;

    @Id
    @Column(name="session_id")
    private Long sessionId;
}
