package com.mice.backoffice.c2v_mice_backoffice_api.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {
    @Bean
    public SimpleModule jsonMapperJava8DateTimeModule() {
        SimpleModule module = new SimpleModule();

        module.addDeserializer(LocalDate.class, new JsonDeserializer<>() {
            @Override
            public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return StringUtils.hasText(jsonParser.getValueAsString()) ? LocalDate.parse(jsonParser.getValueAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
            }
        }).addDeserializer(LocalTime.class, new JsonDeserializer<>() {
            @Override
            public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return StringUtils.hasText(jsonParser.getValueAsString()) ? LocalTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ofPattern("HH:mm:ss")) : null;
            }
        }).addDeserializer(LocalDateTime.class, new JsonDeserializer<>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return StringUtils.hasText(jsonParser.getValueAsString()) ? LocalDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;

            }
        }).addSerializer(LocalDateTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }
        });

        return module;
    }
}
