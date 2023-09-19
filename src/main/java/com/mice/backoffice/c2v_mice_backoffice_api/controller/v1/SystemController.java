package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.CodeDto;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminPasswordRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.system.*;
import com.mice.backoffice.c2v_mice_backoffice_api.service.system.CodeService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.system.GroupService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.system.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "system", description = "시스템 관리 API")
@RequiredArgsConstructor
@RequestMapping("/api/system")
@RestController
public class SystemController {
    private final MenuService menuService;
    private final CodeService codeService;
    private final GroupService groupService;
    private Map<String, Object> params = new LinkedHashMap<>();

    // CodeConfig 파일에서 bean 등록
    private final Map<String, List<CodeDto>> codeMap;

    //region 메뉴
    @GetMapping("/menus")
    public C2VResponse getMenus() throws C2VException {
        params.clear();
        params.put("in_cur_admin_id", RequestUtils.getCurAdminId());
        C2VResponse c2VResponse = menuService.findAll(params);
        return c2VResponse;
    }

    @GetMapping("/menu/{menuSeq}")
    public C2VResponse getMenu(@PathVariable("menuSeq") int menuSeq) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id", RequestUtils.getCurAdminId());
        params.put("in_menu_seq", menuSeq);

        C2VResponse c2VResponse = menuService.findAllById(params);

        return c2VResponse;
    }

    @PostMapping("/menu")
    public C2VResponse addMenu(@RequestBody MenuRequest menuRequest) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id"        , RequestUtils.getCurAdminId());
        params.put("in_menu_no"             , menuRequest.getMenuNo());
        params.put("in_menu_type"           , menuRequest.getMenuType());
        params.put("in_menu_name"           , menuRequest.getMenuName());
        params.put("in_parent_menu_seq"     , menuRequest.getParentMenuSeq());
        params.put("in_link_address"        , menuRequest.getLinkAddress());

        C2VResponse c2VResponse = menuService.add(params);

        return c2VResponse;
    }

    @PutMapping("/menu/{menuSeq}")
    public C2VResponse modifyMenu(@PathVariable("menuSeq") int menuSeq, @RequestBody MenuRequest menuRequest) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id"        , RequestUtils.getCurAdminId());
        params.put("in_menu_seq"            , menuSeq);
        params.put("in_menu_no"             , menuRequest.getMenuNo());
        params.put("in_menu_type"           , menuRequest.getMenuType());
        params.put("in_menu_name"           , menuRequest.getMenuName());
        params.put("in_parent_menu_seq"     , menuRequest.getParentMenuSeq());
        params.put("in_link_address"        , menuRequest.getLinkAddress());


        C2VResponse c2VResponse = menuService.modify(params);

        return c2VResponse;
    }

    @DeleteMapping("/menu/{menuSeq}")
    public C2VResponse deleteMenu(@PathVariable("menuSeq") int menuSeq) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_menu_seq"        , menuSeq);
        C2VResponse c2VResponse = menuService.delete(params);

        return c2VResponse;
    }


    @GetMapping("/menu/authoritys")
    public C2VResponse getMenuAuthoritys(@RequestParam String roleCode) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id", RequestUtils.getCurAdminId());
        params.put("in_role_code", roleCode);
        C2VResponse c2VResponse = menuService.findMenuAuthorityAll(params);

        return c2VResponse;
    }

    @PostMapping("/menu/authority")
    public C2VResponse addMenuAuthority(@RequestBody MenuAuthorityRequest menuAuthorityRequest) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id", RequestUtils.getCurAdminId());
        params.put("in_menu_seq", menuAuthorityRequest.getMenuSeq());
        params.put("in_role_code", menuAuthorityRequest.getRoleCode());
        params.put("in_authority_code", menuAuthorityRequest.getAuthorityCode());
        C2VResponse c2VResponse = menuService.addMenuAuthority(params);

        return c2VResponse;
    }

    @PutMapping("/menu/authority/{authoritySeq}/{authorityCode}")
    public C2VResponse modifyMenuAuthority(@PathVariable("authoritySeq") int authoritySeq, @PathVariable("authorityCode") int authorityCode) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id", RequestUtils.getCurAdminId());
        params.put("in_authority_seq", authoritySeq);
        params.put("in_authority_code", authorityCode);
        C2VResponse c2VResponse = menuService.modifyMenuAuthority(params);

        return c2VResponse;
    }


    @DeleteMapping("/menu/authority/{authoritySeq}")
    public C2VResponse deleteMenuAuthority(@PathVariable("authoritySeq") int authoritySeq) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id", RequestUtils.getCurAdminId());
        params.put("in_authority_code", authoritySeq);
        C2VResponse c2VResponse = menuService.deleteMenuAuthority(params);
        return c2VResponse;
    }
    //endregion


    //region 코드
    @GetMapping("/codes")
    public C2VResponse getCodes() throws Exception {

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(codeMap)
                .build();
    }

    @GetMapping("/code/{cdTyp}")
    public C2VResponse getCodes(@PathVariable(value = "cdTyp") String cdTyp) throws C2VException {
        C2VResponse c2VResponse = codeService.findAllByCdTyp(cdTyp);
        return c2VResponse;
    }

    @GetMapping("/code/{cdTyp}/{cd}")
    public C2VResponse getCode(@PathVariable("cdTyp") String cdTyp, @PathVariable("cd") int cd) throws C2VException {
        C2VResponse c2VResponse = codeService.findByCdTypAndCd(cdTyp, cd);
        return c2VResponse;
    }

    @Operation(summary = "공간템플릿코드리스트", description = "공간에서 사용하는 코드리스트를 반환한다.")
    @GetMapping("/code/spacetemplate")
    public C2VResponse getSpaceTemplate() throws C2VException {
        return codeService.getSpaceTemplate();
    }

    @Operation(summary = "공간템플릿코드리스트 - 타입별", description = "공간에서 사용하는 코드리스트를 반환한다.")
    @GetMapping("/code/spacetemplate/{cdTyp}")
    public C2VResponse getSpaceTemplate(@PathVariable("cdTyp") String cdTyp) throws C2VException {
        return codeService.getSpaceTemplate(cdTyp);
    }

    /*
        @PostMapping("/code/{cdTyp}")
        public C2VResponse addCode(@PathVariable("cdTyp") String cdTyp, @RequestBody CodeInfo codeInfo) throws C2VException {

            params = new LinkedHashMap<>();
            params.put("in_cd_typ", cdTyp);
            params.put("in_cd", codeInfo.getCd());
            params.put("in_cd_nm", codeInfo.getCdNm());
            params.put("in_cd_desc", codeInfo.getCdDesc());
            params.put("in_use_yn", codeInfo.getUseYn());

            C2VResponse c2VResponse = codeService.add(params);

            return c2VResponse;
        }

        @PutMapping("/code/{cdTyp}/{cd}")
        public C2VResponse modifyCode(@PathVariable("cdTyp") String cdTyp, @PathVariable("cd") int cd, @RequestBody CodeInfo codeInfo) throws C2VException {

            params = new LinkedHashMap<>();
            params.put("in_cd_typ", cdTyp);
            params.put("in_cd", cd);
            params.put("in_cd_nm", codeInfo.getCdNm());
            params.put("in_cd_desc", codeInfo.getCdDesc());
            params.put("in_use_yn", codeInfo.getUseYn());
            C2VResponse c2VResponse = codeService.modify(params);

            return c2VResponse;
        }

        @DeleteMapping("/code/{cdTyp}/{cd}")
        public C2VResponse deleteCode(@PathVariable("cdTyp") String cdTyp, @PathVariable("cd") int cd) throws C2VException {

            params = new LinkedHashMap<>();
            params.put("in_cd_typ", cdTyp);
            params.put("in_cd", cd);
            C2VResponse c2VResponse = codeService.delete(params);

            return c2VResponse;
        }

    */
    @GetMapping("/code/sync/jsonToDb")
    public C2VResponse jsonToDb() throws C2VException, IOException {
        return codeService.jsonTOdb();
    }


    //endregion


    //region 그룹
    @Operation(summary = "그룹 리스트 조회", description = "그룹 리스트 조회")
    @GetMapping("/groups")
    public C2VResponse findAll(GroupListRequest req) throws C2VException {
        return groupService.findAll(req);
    }

    @Operation(summary = "그룹 상세정보 조회", description = "그룹 상세정보 조회")
    @GetMapping("/group/{groupId}")
    public C2VResponse findAllById(@PathVariable("groupId") int groupId) throws C2VException {
        return groupService.findAllById(groupId);
    }

    @Operation(summary = "그룹 등록", description = "그룹등록")
    @PostMapping("/group")
    public C2VResponse addGroup(@RequestBody GroupRequest req) throws C2VException {
        return groupService.addGroup(req);
    }

    @Operation(summary = "그룹 수정", description = "그룹 수정")
    @PutMapping("/group/{groupId}")
    public C2VResponse modifyGroup(@PathVariable("groupId") int groupId, @RequestBody GroupRequest req) throws C2VException {
        return groupService.modifyGroup(groupId, req);
    }

    @Operation(summary = "그룹 삭제", description = "그룹삭제")
    @DeleteMapping("/group/{groupId}")
    public C2VResponse deleteGroup(@PathVariable("groupId") int groupId) throws C2VException {
        return groupService.deleteGroup(groupId);
    }

    //endregion
}
