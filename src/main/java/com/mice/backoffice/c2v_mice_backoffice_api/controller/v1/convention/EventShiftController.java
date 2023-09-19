package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.*;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.EventShiftService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/events/temp")
public class EventShiftController {
    // Service
    private final EventShiftService eventShiftService;


    @Operation(summary = "행사 리스트 (임시저장)", description = "임시저장 행사리스트 조회")
    @GetMapping
    public C2VResponse findEventForShift(EventListRequest req) throws Exception {
        return eventShiftService.findEventForShift(req);
    }


    @GetMapping("/{eventId}")
    public C2VResponse get(@PathVariable("eventId") long eventId) throws Exception {
        EventGetResponse eventGetResponse = eventShiftService.get(eventId);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(eventGetResponse)
                .build();
    }

    @PostMapping
    public C2VResponse create(@Valid @RequestBody EventShiftCreateRequest req,
                              HttpServletResponse response) throws Exception {
        EventCreateResponse eventCreateResponse = eventShiftService.create(req);

        response.setStatus(HttpStatus.CREATED.value());

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(eventCreateResponse)
                .build();
    }

    @PutMapping("/{eventId}")
    public C2VResponse update(@Valid @RequestBody EventShiftUpdateRequest req,
                              @PathVariable("eventId") long eventId) throws Exception {
        eventShiftService.update(req, eventId);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @PostMapping("/mig/{eventId}")
    public C2VResponse migration(@PathVariable("eventId") long eventId) {
        eventShiftService.migration(eventId);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @DeleteMapping("/{eventId}")
    public C2VResponse delete(@PathVariable("eventId") long eventId) throws Exception {
        eventShiftService.delete(eventId);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }
}
