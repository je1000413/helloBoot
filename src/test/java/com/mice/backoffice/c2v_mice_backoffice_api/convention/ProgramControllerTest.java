package com.mice.backoffice.c2v_mice_backoffice_api.convention;

import com.com2verse.platform.exception.C2VException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention.EventController;
import com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention.ProgramController;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program.ProgramCreateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.charset.Charset.defaultCharset;

@SpringBootTest
@Transactional
@Rollback(value = false)
@ActiveProfiles("local")
class ProgramControllerTest {
    private final String PROGRAM_CREATE_MOCK_DATA_PATH = "/mock/program/ProgramCreateMock.json";
    private final Gson gson;

    @Autowired
    private ProgramController controller;

    @Autowired
    ResourceLoader resourceLoader;
    ProgramControllerTest() {
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                        LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).create();
    }

    @Test
    void create() throws C2VException, IOException {
        String mockData = StreamUtils.copyToString(new ClassPathResource(PROGRAM_CREATE_MOCK_DATA_PATH).getInputStream(), defaultCharset());
        var req = gson.fromJson(mockData, ProgramCreateRequest.class);

        //controller.create(req);
    }
}