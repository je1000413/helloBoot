package com.mice.backoffice.c2v_mice_backoffice_api.service.login.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SecretLogic;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.redis.Token;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.LoginRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.LoginResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.login.PrincipalDetails;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.login.LoginResponseRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.AdminRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.system.TokenRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.login.LoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

//@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService, UserDetailsService {

    private final AdminRepository adminRepository;
    private final LoginResponseRepository loginResponseRepository;
    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity AdminEntity = adminRepository.findByAdminId(Long.valueOf(username));
        return new PrincipalDetails(AdminEntity);
    }

    @Override
    public AdminEntity findByAccountNameAndAccountPassword(String accountName, String accountPassword) throws C2VException {

        AdminEntity adminEntity = adminRepository.findByAccountName(accountName);
        if(adminEntity == null) throw new C2VException(ResponseCode.BAD_REQUEST, "존재하지 않는 계정입니다.");

        String encodePassword = "";
        if(adminEntity.getAccountPassword().contains("{bcrypt}")) encodePassword = adminEntity.getAccountPassword().replace("{bcrypt}", "");
        else encodePassword = adminEntity.getAccountPassword();

        if(!passwordEncoder.matches(accountPassword, encodePassword)){
            adminEntity.updateForPasswordFail();
            if(adminEntity.getPasswordTryCount() > 10) throw new C2VException(ResponseCode.BAD_REQUEST, "비밀번호가 10회 이상 틀렸습니다");
            else throw new C2VException(ResponseCode.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        return adminEntity;
    }

    @Override
    public AdminEntity findByAdminId(String adminId) {
        return adminRepository.findByAdminId(Long.valueOf(adminId));
    }

    @Override
    public List<LoginResponse> findLoginResponse(Long adminId) {
        return loginResponseRepository.findLoginResponse(adminId);
    }

    @Override
    public C2VResponse join(AdminEntity adminEntity) throws C2VException {

        if(adminRepository.findByAccountName(adminEntity.getAccountName()) != null) throw new C2VException(400, "이미 존재하는 계정입니다.");

        String accountPassword = "{bcrypt}" + passwordEncoder.encode(adminEntity.getAccountPassword());
        adminEntity.update(accountPassword);

        adminRepository.save(adminEntity);

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("회원가입 완료").build();
    }

    @Override
    public C2VResponse login(LoginRequest loginRequest) throws C2VException {
        AdminEntity adminEntity = findByAccountNameAndAccountPassword(loginRequest.getAccountName(), loginRequest.getAccountPassword());
        Long adminId = adminEntity.getAdminId();

        Map<String, Object> data = loginProcess(adminId, adminEntity);


        String refreshToken = Jwts.builder()
                .setHeader(new HashMap<String, Object>())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.Auth.REFRESH_TOKEN_EXPIRATION))
                .signWith(SecretLogic.convertStringToPrivateKey(Constants.Auth.API_TOKEN_PRIVATE_KEY), SignatureAlgorithm.ES512)
                .compact();
        try {
            tokenRepository.save(new Token(adminId, refreshToken));
        } catch (Exception ex) {
            System.out.println("token save failed : " + ex.getMessage());
        }
        data.put("refreshToken", refreshToken);

        List<LoginResponse> loginResponseList = loginResponseRepository.findLoginResponse(adminId);
        data.put("role", loginResponseList.get(0).getRole());
        data.put("menu", loginResponseList);

        adminEntity.updateForLastLoginDatetime();
        adminRepository.save(adminEntity);

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("로그인 성공").data(data).build();
    }

    public Map<String,Object> loginProcess(Long adminId, AdminEntity adminEntity) {

        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("adminId", adminId);
        payloadMap.put("permission", "ADMIN");
        System.out.println(payloadMap);

        Gson gson = new Gson();
        JsonObject jsonObj = gson.toJsonTree(payloadMap).getAsJsonObject();

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(Constants.Auth.API_TOKEN_CLAIMS_ISSUER, Constants.Auth.API_TOKEN_ISSUER);
        claimsMap.put(Constants.Auth.API_TOKEN_CLAIMS, Base64.getEncoder().encodeToString(SecretLogic.AesEncrypt(jsonObj.toString())));

        String accessToken = Jwts.builder()
                .setHeader(new HashMap<String, Object>())
                .setClaims(claimsMap)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.Auth.API_TOKEN_EXPIRATION))
                .signWith(SecretLogic.convertStringToPrivateKey(Constants.Auth.API_TOKEN_PRIVATE_KEY), SignatureAlgorithm.ES512)
                .compact();


        Map<String, Object> data = new HashMap<>();
        data.put("adminId", adminId);
        data.put("accessToken", Constants.Auth.API_TOKEN_PREFIX + accessToken);
        data.put("psswdChgReq",false);

        if(adminEntity.getLastLoginDatetime() == null || "".equals(adminEntity.getLastLoginDatetime())) data.put("psswdChgReq",true);

        String encodePassword = "";
        if(adminEntity.getAccountPassword().contains("{bcrypt}")) encodePassword = adminEntity.getAccountPassword().replace("{bcrypt}", "");
        else encodePassword = adminEntity.getAccountPassword();

        if(passwordEncoder.matches(adminEntity.getAccountName(), encodePassword))data.put("psswdChgReq",true);

        return data;
    }

    @Override
    public C2VResponse logout(String adminId) {
        tokenRepository.deleteById(Long.valueOf(adminId));
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("로그아웃").build();
    }

    @Override
    public C2VResponse addToken(Token token) {
        tokenRepository.save(token);
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("RefreshToken 저장 완료").build();
    }

    @Override
    public C2VResponse findRefreshTokenById(Long id) {
        String refreshToken = null;
        Optional<Token> token = tokenRepository.findById(id);
        if(token != null){
            refreshToken = token.get().getRefreshToken();
        }
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("Refresh Token 조회 성공").data(refreshToken).build();
    }

    @Override
    public C2VResponse reissue(String id) {
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("성공").build();
    }

}
