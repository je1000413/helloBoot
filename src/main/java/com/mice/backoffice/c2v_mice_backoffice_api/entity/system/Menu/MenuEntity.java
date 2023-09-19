package com.mice.backoffice.c2v_mice_backoffice_api.entity.system.Menu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class MenuEntity {

    @Column(name = "parent_menu_seq")
    private Integer parentMenuSeq;
    @Id
    @Column(name = "menu_seq")
    private Integer menuSeq;

    @Column(name = "menu_no")
    private Integer menuNo;

    @Column(name = "menu_type")
    private Integer menuType;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "link_address")
    private String linkAddress;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "menu_sort_name")
    private String menuSortName;

}
