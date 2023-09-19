package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1;

import com.mice.backoffice.c2v_mice_backoffice_api.service.RedisService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/api/system-test")
@RestController
public class TestController {
    private final RedisService redisService;

    @GetMapping
    @Hidden
    public void test() {
        Set<Object> result = redisService.test();

        List<Long> list = result
                .stream()
                .map(x -> Long.parseLong(x.toString()))
                .toList();
    }

}
