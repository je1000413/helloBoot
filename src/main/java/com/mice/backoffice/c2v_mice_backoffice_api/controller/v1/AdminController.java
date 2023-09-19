package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.*;
import com.mice.backoffice.c2v_mice_backoffice_api.service.admin.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api")
public class AdminController {

    private final AdminService adminService;

    // TODO 삭제
    @Operation(summary = "관리자 권한 체크", description = "메뉴에 대한 권한 체크")
    @GetMapping("/admin/checkAuth")
    public C2VResponse checkAuth(@RequestParam Long curAdminId, @RequestParam Integer curMenuSeq, @RequestParam String method) throws UncategorizedSQLException {
        return adminService.checkAuth(curAdminId, curMenuSeq, method);
    }

    @Operation(summary = "관리자 리스트 조회", description = "c2v운영자, 주최사, 오퍼레이터등 role에 따라 조회 가능, 검색조건-코드조회(ADMIN_SEARCH_TYPE) ")
    @GetMapping("/admins")
    public C2VResponse findAll(AdminListRequest req) throws C2VException {
        return adminService.findAll(req);
    }

    @Operation(summary = "관리자 상세정보 조회", description = "관리자 상세정보 조회")
    @GetMapping("/admin/{adminId}")
    public C2VResponse findAllById(@PathVariable("adminId") long adminId) throws C2VException {
        return adminService.findAllById(adminId);
    }

    @Operation(summary = "관리자 등록", description = "C2V운영자, 주최사, 오퍼레이터, 기타등등 등록 함")
    @PostMapping("/admin")
    public C2VResponse addAdmin(@RequestBody AdminRequest req) throws C2VException {
        return adminService.addAdmin(req);
    }


    @Operation(summary = "관리자 계정 중복체크", description = "관리자계정 ID 중복체크 용")
    @GetMapping("/admin/accountnamecheck/{accountName}")
    public C2VResponse getAdminAccountNameCheck(@PathVariable("accountName") String accountName) throws C2VException {
        return adminService.getAdminAccountNameCheck(accountName);
    }

    @Operation(summary = "관리자 수정", description = "C2V운영자, 주최사, 오퍼레이터, 기타등등 수정 함")
    @PutMapping("/admin/{adminId}")
    public C2VResponse modifyAdmin(@PathVariable("adminId") long adminId, @RequestBody AdminRequest req) throws C2VException {
        return adminService.modifyAdmin(adminId, req);
    }

    @Operation(summary = "관리자 비번 초기화", description = "계정 비밀번호 초기화")
    @PutMapping("/admin/{adminId}/password")
    public C2VResponse modifyAdminPassword(@PathVariable("adminId") long adminId, @RequestBody AdminPasswordRequest req) throws C2VException {
        return adminService.modifyAdminPassword(adminId, req);
    }

    @Operation(summary = "관리자 삭제", description = "C2V운영자, 주최사, 오퍼레이터, 기타등등 삭제 함")
    @DeleteMapping("/admin/{adminId}")
    public C2VResponse deleteAdmin(@PathVariable("adminId") long adminId) throws C2VException {
        return adminService.deleteAdmin(adminId);
    }

    @Operation(summary = "주최사리스트", description = "주최사 리스트 조회")
    @GetMapping("/domains")
    public C2VResponse findDomain(@RequestParam(required = false, defaultValue = "")AdminSupports.DomainSearchType searchType,  @RequestParam(required = false, defaultValue = "")String searchValue) throws C2VException {

        searchType = searchType == null ? AdminSupports.DomainSearchType.NO_SELECTED : searchType;
        var req = DomainSearchRequest.builder().searchType(searchType).searchValue(searchValue).build();
        return adminService.findDomain(req);
    }

    @Operation(summary = "주최사등록", description = "주최사 등록")
    @PostMapping("/domain")
    public C2VResponse addDomain(@RequestBody DomainRequest req) throws C2VException {
        return adminService.addDomain(req);
    }

    @Operation(summary = "주최사수정", description = "주최사 수정")
    @PutMapping("/domain/{domainId}")
    public C2VResponse modifyDomain(@PathVariable("domainId") int domainId, @RequestBody DomainRequest req) throws C2VException {
        return adminService.modifyDomain(domainId, req);
    }

    @Operation(summary = "주최사삭제", description = "주최사삭제")
    @DeleteMapping("/domain/{domainId}")
    public C2VResponse deleteDomain(@PathVariable("domainId") int domainId) throws C2VException {
        return adminService.deleteDomain(domainId);
    }

}
