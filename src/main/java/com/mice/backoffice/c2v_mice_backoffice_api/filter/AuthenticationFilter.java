package com.mice.backoffice.c2v_mice_backoffice_api.filter;

import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mice.backoffice.c2v_mice_backoffice_api.common.ResponseUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.AdminRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.login.LoginService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AdminRepository adminRepository;
    private final LoginService loginService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            ResponseUtils.setResponseDefaultAttribute(response);

            System.out.println("인증 요청 : " + request.getServletPath());

            byte [] inputStreamBytes = StreamUtils.copyToByteArray((request.getInputStream()));
            Map<String, String> jsonRequest = new ObjectMapper().readValue(inputStreamBytes, Map.class);

            String accountName = jsonRequest.get("accountName");
            String accountPassword = jsonRequest.get("accountPassword");
            AdminEntity adminEntity = loginService.findByAccountNameAndAccountPassword(accountName,accountPassword);
            String adminId = String.valueOf(adminEntity.getAdminId());


            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(adminId, accountPassword);

            return authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                Gson gson = new Gson();
                JsonObject resObj = gson.toJsonTree(C2VResponse.builder().code(ResponseCode.BAD_REQUEST.status()).msg("로그인 정보가 다릅니다.").build()).getAsJsonObject();

                response.getWriter().write(resObj.toString());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}

