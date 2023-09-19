package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.redis.Token;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.LoginRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 로그인 API Controller
 * @author	JiEun Chun
 * @email	je1000@com2us.com
 * @since	2023.02.16
 */

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // TODO 삭제
    @GetMapping("/test")
    public String findPasswordEncoderValue(@RequestParam String pwd) throws C2VException {
        return passwordEncoder.encode(pwd);
    }
    @Operation(summary = "회원가입", description = "MICE 회원가입")
    @PostMapping("/join")
    public C2VResponse join(@RequestBody AdminEntity adminEntity) throws C2VException {
        return loginService.join(adminEntity);
    }

    @Operation(summary = "로그인", description = "MICE 로그인")
    @PostMapping("/login")
    public C2VResponse login(@RequestBody LoginRequest loginRequest) throws C2VException {
        return loginService.login(loginRequest);
    }

    @Operation(summary = "로그아웃", description = "MICE 로그아웃")
    @GetMapping("/logout")
    public C2VResponse logout(@RequestParam String adminId) {
        return loginService.logout(adminId);
    }

    @Operation(summary = "RefreshToken 발행", description = "RefreshToken 발행")
    @PostMapping("/refreshToken")
    public C2VResponse addRefreshToken(@RequestBody Token token) throws C2VException {
        return loginService.addToken(token);
    }

    @Operation(summary = "RefreshToken 조회", description = "RefreshToken 조회")
    @GetMapping("/refreshToken")
    public C2VResponse findRefreshTokenById(@RequestParam Long id) throws C2VException {
        return loginService.findRefreshTokenById(id);
    }

    @Operation(summary = "Token 재발행", description = "Token 재발행")
    @GetMapping("/reissue")
    public C2VResponse reissue(@RequestParam String id) throws C2VException {
        return loginService.reissue(id);
    }
}
