package com.mice.backoffice.c2v_mice_backoffice_api.config;

import feign.Request;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class EncodeConfig  implements Encoder {
    private final Encoder delegate;

    public EncodeConfig() {
        this.delegate = new SpringFormEncoder();;
    }
    private static final String CONTENT_TYPE = "multipart/form-data";

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        if (object instanceof Map && ((Map<?, ?>) object).size() == 1 && ((Map<?, ?>) object).keySet().iterator().next() instanceof String) {
            String key = (String) ((Map<?, ?>) object).keySet().iterator().next();
            Object value = ((Map<?, ?>) object).get(key);
            delegate.encode(Collections.singletonMap(key, value), MAP_STRING_WILDCARD, template);
        } else {
            delegate.encode(object, bodyType, template);
        }
    }
}