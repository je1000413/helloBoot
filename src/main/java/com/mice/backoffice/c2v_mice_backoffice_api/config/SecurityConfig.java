package com.mice.backoffice.c2v_mice_backoffice_api.config;

import com.mice.backoffice.c2v_mice_backoffice_api.filter.AuthenticationFilter;
import com.mice.backoffice.c2v_mice_backoffice_api.filter.AuthorizationFilter;
import com.mice.backoffice.c2v_mice_backoffice_api.filter.LoggerFilter;
import com.mice.backoffice.c2v_mice_backoffice_api.filter.RequestFilter;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.AdminRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.admin.AdminService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final LoggerFilter loggerFilter;
    private final RequestFilter requestFilter;
    private final AdminRepository adminRepository;
    private final LoginService loginService;
    private final AdminService adminService;


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        AuthenticationConfiguration authenticationConfiguration = http.getSharedObject(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = authenticationManager(authenticationConfiguration);

        http.addFilter(corsFilter)
                .addFilterBefore(requestFilter,AuthenticationFilter.class)
                .addFilterBefore(loggerFilter, AuthenticationFilter.class)
                .addFilter(new AuthenticationFilter(authenticationManager, adminRepository, loginService))
                .addFilter(new AuthorizationFilter(authenticationManager, adminRepository, loginService, adminService))
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll()
        ;

        http.formLogin();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }
}