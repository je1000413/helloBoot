package com.mice.backoffice.c2v_mice_backoffice_api.convention;

import com.com2verse.platform.object.C2VResponse;
import com.google.gson.*;
import com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention.EventController;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventShiftCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventUpdateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static java.nio.charset.Charset.defaultCharset;

@SpringBootTest
@Transactional
@Rollback(value = false)
@ActiveProfiles("local")
class EventControllerTest {
    private final String EVENT_CREATE_MOCK_DATA_PATH = "/mock/event/EventCreateMock.json";
    private final String EVENT_UPDATE_MOCK_DATA_PATH = "/mock/event/EventUpdateMock.json";
    private final Gson gson;

    private final String JWT_TOKEN = "Bearer eyJhbGciOiJFUzUxMiJ9.eyJkYXRhIjoid29EeUNVZDU2QWpHOUNMSGIxcTZPMmliNmJYYVVLSzk1alFQWjFLWFlxZ1grRFBDMWd6ZjFaNENvU3pRUlR5RiIsImlzcyI6ImMydl9vZmZpY2VfYmFja29mZmljZV9hcGkiLCJleHAiOjE2ODE0NDYzOTV9.AMLmwUlw2-y_999Rc_s94Sr0YqJtUvzvZx8U0BIWgDT3RCd8hNm2govMjYIPFInVhp10vKR8UL0jgly4WHQPqid9ADuC4Q3fseBvm1d7PcBdtIXvvcE1kXu9XKbNTGKyKPnxjo7c906XhKugN9bb5rmH8VUTFh38sK1J40ERXa03vNek";
    private final WebClient webClient;

    @Autowired
    private EventController controller;

    @Autowired
    ResourceLoader resourceLoader;
    EventControllerTest() {
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                        LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).create();

        webClient = WebClient
                .builder()
                .baseUrl("http://localhost:20004")
                .defaultHeader(HttpHeaders.AUTHORIZATION, JWT_TOKEN)
                .build();
    }

    @Test
    void create() throws Exception {
        String mockData = StreamUtils.copyToString(new ClassPathResource(EVENT_CREATE_MOCK_DATA_PATH).getInputStream(), defaultCharset());
        var req = gson.fromJson(mockData, EventCreateRequest.class);


        controller.create(req, null);
        //Assertions.assertThat(result.getCode()).isEqualTo(Constants.ResponseCode.SUCCESS.value());
    }

    @Test
    void update() throws Exception {
        String mockData = StreamUtils.copyToString(new ClassPathResource(EVENT_UPDATE_MOCK_DATA_PATH).getInputStream(), defaultCharset());
        var req = gson.fromJson(mockData, EventUpdateRequest.class);
        long eventId = 22;

        controller.update(req, eventId);
    }

    @Test
    void get() throws Exception {
        long eventId = 24;
        C2VResponse c2VResponse = controller.get(eventId);

        System.out.println("c2VResponse.getData() = " + c2VResponse.getData());
    }
}