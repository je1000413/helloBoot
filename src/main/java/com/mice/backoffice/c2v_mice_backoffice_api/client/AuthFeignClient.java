package com.mice.backoffice.c2v_mice_backoffice_api.client;

import com.com2verse.platform.object.C2VResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "auth", url = "${auth.domain}")
public interface AuthFeignClient {
    @PostMapping("/api/auth/v1/system/token")
    C2VResponse getSystemToken(@RequestBody Map<String, String> request);

    @PostMapping("/api/auth/v1/partner/sign")
    C2VResponse getPartnerToken(@RequestHeader("Authorization") String authorization, @RequestBody Map<String, Object> request);
}