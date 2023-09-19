package com.mice.backoffice.c2v_mice_backoffice_api.entity.system.group;


import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminResponseEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.system.Menu.MenuEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class GroupResponseEntity extends GroupEntity{

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "state_name")
    private String stateName;

    @Transient
    private List<GroupResponseEntity.GroupMenuResponseEntity> groupMenus;


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Entity
    public static class GroupMenuResponseEntity extends MenuEntity {
        @Column(name = "depth")
        private Integer depth;
        @Column(name = "menu_sort_name")
        private String menuSortName;
        @Column(name="authority_code")
        private Integer authorityCode;
        @Column(name="authority_name")
        private String authorityName;
    }
}
