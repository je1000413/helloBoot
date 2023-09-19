package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class ConventionEntity {

    @Id
    @Column(name = "event_seq")
    private Long eventSeq;


}
