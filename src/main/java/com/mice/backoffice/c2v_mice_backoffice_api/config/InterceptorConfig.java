package com.mice.backoffice.c2v_mice_backoffice_api.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class InterceptorConfig {
    @Value("${groupServer.domain}")
    private String url;
    @Value("${groupServer.port}")
    private int port;

    private final String URL_FORMAT = "%s/%s";

    public RequestInterceptor contextInterception() {
        return template -> {
            String url = template.url();

            if (url.contains("$url")) {
                url = url.replace("$url", String.format(URL_FORMAT, url, port));
                template.uri(url);
            }
        };
    }
}
