package com.mice.backoffice.c2v_mice_backoffice_api.model.system;

import jakarta.persistence.Column;
import lombok.Data;


@Data
public class MenuResponse {

    @Column(name = "depth")
    private Integer depth;

    @Column(name = "parent_menu_seq")
    private Integer parentMenuSeq;

    @Column(name = "menu_seq")
    private Integer menuSeq;

    @Column(name = "menu_no")
    private Integer menuNo;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "link_address")
    private String linkAddress;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "admin_role_code")
    private Integer adminRoleCode;

    @Column(name = "admin_role_name")
    private String adminRoleName;

    @Column(name = "menu_sort")
    private String menuSort;
}
