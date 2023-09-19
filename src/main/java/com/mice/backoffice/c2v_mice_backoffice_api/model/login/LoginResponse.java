package com.mice.backoffice.c2v_mice_backoffice_api.model.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    @Column(name = "depth")
    private @Getter Integer depth;
    @Column(name = "parent_menu_seq")
    private @Getter Integer parentMenuSeq;
    @Column(name = "menu_seq")
    private @Getter Integer menuSeq;
    @Column(name = "menu_no")
    private @Getter Integer menuNo;
    @Column(name = "menu_type")
    private @Getter Integer menuType;
    @Column(name = "menu_name")
    private @Getter String menuName;
    @Column(name = "link_address")
    private @Getter String linkAddress;
    @Column(name = "use_yn")
    private @Getter String useYn;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "authority_code")
    private Integer authorityCode;
    @Column(name = "authority_name")
    private String authorityName;
    @Column(name = "menu_sort_name")
    private @Getter String menuSortName;
    @Column(name = "menu_sort")
    private @Getter String menuSort;

    private List<LoginResponse> children = new ArrayList<>();

    public Integer getParentMenuSeq() {
        return this.parentMenuSeq;
    }
    public Integer getMenuSeq() {
        return this.menuSeq;
    }
    public List<LoginResponse> getChildren() {
        return this.children;
    }
    public Map<String, Object> getRole() {

        Map<String, Object> role = new HashMap<>();
        role.put("groupId", this.groupId);
        role.put("groupName", this.groupName);

        return role;
    }
    public Map<String, Object> getAuthority() {

        Map<String, Object> role = new HashMap<>();
        role.put("authorityCode", this.authorityCode);
        role.put("authorityName", this.authorityName);

        return role;
    }

    public static List<LoginResponse> formatTree(List<LoginResponse> list) {
        LoginResponse root = new LoginResponse();
        LoginResponse node = new LoginResponse();
        List<LoginResponse> treelist = new ArrayList<>();
        List<LoginResponse> parentnodes = new ArrayList<>();
        if (list != null && list.size() > 0){
            root = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                node = list.get(i);
                if (root.getMenuSeq().equals(node.getParentMenuSeq()) ) {
                    parentnodes.add(node);
                    root.getChildren().add(node);
                }
                else {
                    getChildrenNodes(parentnodes, node);
                    parentnodes.add(node);
                }
            }
        }
        treelist.add(root);
        return treelist;
    }

    private static void getChildrenNodes(List<LoginResponse> parentnodes, LoginResponse node) {
        for (int i = parentnodes.size() - 1; i >= 0; i--) {
            LoginResponse pnode = parentnodes.get(i);
            if (node.getParentMenuSeq().equals(pnode.getMenuSeq())) {
                pnode.getChildren().add(node);
                return;
            }
        }
    }
}
