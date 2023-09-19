package com.mice.backoffice.c2v_mice_backoffice_api.entity.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.base.JpaBaseTimeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventHostEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "domain")
@Getter
public class DomainEntity extends JpaBaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Column(name = "domain_id") int domainId;
    private @Column(name = "domain_name") String domainName;
    private @Column(name = "use_yn") char useYn;

    @OneToMany(mappedBy = "domain")
    private List<EventHostEntity> eventHosts = new ArrayList<>();
}
