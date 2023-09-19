package com.mice.backoffice.c2v_mice_backoffice_api.entity.system.Menu;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class MenuResponseEntity extends MenuEntity {
    @Column(name = "depth")
    private Integer depth;
}