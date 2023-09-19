package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.admin.AdminPasswordRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.LoginRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.OpAdminCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.OpAdminListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.OpAdminUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.admin.AdminService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.OperatorAdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/operator")
public class OperatorAdminController {

    private final OperatorAdminService operatorAdminService;
    private final AdminService adminService;

    @Operation(summary = "파트너 로그인", description = "파트너 로그인")
    @PostMapping("/partner/login")
    public C2VResponse partnerLogin(@RequestBody LoginRequest loginRequest) throws C2VException  {
        return operatorAdminService.partnerLogin(loginRequest);
    }

    @Operation(summary = "파트너 인증", description = "파트너 인증")
    @PostMapping("/partner/auth")
    public C2VResponse partnerLogin(@RequestBody LoginRequest loginRequest, @RequestParam int authNumber) throws C2VException  {
        return operatorAdminService.partnerAuth(loginRequest, authNumber);
    }

    @Operation(summary = "오퍼레이터 로그인", description = "오퍼레이터 로그인")
    @PostMapping("/login")
    public C2VResponse login(@RequestBody LoginRequest loginRequest) throws C2VException  {
        return operatorAdminService.login(loginRequest);
    }

    @Operation(summary = "오퍼레이터 계정 리스트 조회", description = "오퍼레이터 계정 리스트 조회")
    @PostMapping("/list")
    public C2VResponse list(@RequestBody OpAdminListRequest request) {
        return operatorAdminService.list(request);
    }

    @Operation(summary = "오퍼레이터 계정 조회", description = "오퍼레이터 계정 조회")
    @GetMapping("/{adminId}")
    public C2VResponse get(@PathVariable(name = "adminId") long adminId) throws C2VException {
        return operatorAdminService.get(adminId);
    }

    @Operation(summary = "오퍼레이터 계정 생성", description = "오퍼레이터 계정 생성")
    @PostMapping()
    public C2VResponse create(@RequestBody OpAdminCreateRequest request) throws C2VException, NoSuchAlgorithmException, IOException {
        return operatorAdminService.create(request);
    }

    @Operation(summary = "오퍼레이터 계정 수정", description = "오퍼레이터 계정 수정")
    @PutMapping("/{adminId}")
    public C2VResponse update(@PathVariable(name = "adminId") long adminId, @RequestBody OpAdminUpdateRequest request) throws C2VException, NoSuchAlgorithmException, IOException {
        return operatorAdminService.update(adminId, request);
    }

    @Operation(summary = "오퍼레이터 계정 비밀번호 초기화", description = "오퍼레이터 계정 수정")
    @PutMapping("/password/{adminId}/reset")
    public C2VResponse getNewPassword(@PathVariable(name = "adminId") long adminId) throws C2VException, NoSuchAlgorithmException, IOException {
        return operatorAdminService.modifyAdminPassword(adminId);
    }

    @Operation(summary = "오퍼레이터 계정 비밀번호 변경", description = "오퍼레이터 계정 수정")
    @PutMapping("/password/{adminId}")
    public C2VResponse modifyNewPassword(@PathVariable(name = "adminId") long adminId, @RequestBody AdminPasswordRequest req) throws C2VException, NoSuchAlgorithmException, IOException {
        return operatorAdminService.modifyNewPassword(adminId, req);
    }

    @Operation(summary = "오퍼레이터 계정 중복체크", description = "관리자계정 ID 중복체크 용")
    @GetMapping("/check/{accountName}")
    public C2VResponse getAdminAccountNameCheck(@PathVariable("accountName") String accountName) throws C2VException {
        return operatorAdminService.getAdminAccountNameCheck(accountName);
    }

}
