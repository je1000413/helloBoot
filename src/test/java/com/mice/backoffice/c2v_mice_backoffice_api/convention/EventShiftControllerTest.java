package com.mice.backoffice.c2v_mice_backoffice_api.convention;

import com.com2verse.platform.exception.C2VException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention.EventController;
import com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention.EventShiftController;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventShiftCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventShiftUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.charset.Charset.defaultCharset;

@SpringBootTest
@Transactional
@Rollback(value = false)
@ActiveProfiles("local")
class EventShiftControllerTest {
    private final String EVENT_SHIFT_CREATE_MOCK_DATA_PATH = "/mock/event/EventShiftCreateMock.json";
    private final String EVENT_SHIFT_UPDATE_MOCK_DATA_PATH = "/mock/event/EventShiftUpdateMock.json";
    private final Gson gson;

    @Autowired
    private EventShiftController controller;

    @Autowired
    ResourceLoader resourceLoader;
    EventShiftControllerTest() {
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                        LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).create();
    }

    @Test
    void create() throws Exception {
        String mockData = StreamUtils.copyToString(new ClassPathResource(EVENT_SHIFT_CREATE_MOCK_DATA_PATH).getInputStream(), defaultCharset());
        var req = gson.fromJson(mockData, EventShiftCreateRequest.class);

        //controller.create(req);
        //Assertions.assertThat(result.getCode()).isEqualTo(Constants.ResponseCode.SUCCESS.value());
    }

    @Test
    void update() throws Exception {
        String mockData = StreamUtils.copyToString(new ClassPathResource(EVENT_SHIFT_UPDATE_MOCK_DATA_PATH).getInputStream(), defaultCharset());
        var req = gson.fromJson(mockData, EventShiftUpdateRequest.class);

        controller.update(req, 23);
        //Assertions.assertThat(result.getCode()).isEqualTo(Constants.ResponseCode.SUCCESS.value());
    }

    @Test
    void get() throws C2VException {
        long eventId = 23;

        //controller.get(eventId);
    }

    @Test
    void migration() {
        long eventId = 24;

        controller.migration(eventId);
    }
}
