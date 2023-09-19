package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants.ResponseCode;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.CodeDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LoungeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventHostEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventShiftCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventUpdateRequest;

import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.*;

import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.LoungeRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.EventHostService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.EventService;
import com.mice.backoffice.c2v_mice_backoffice_api.slave.service.EventListService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/events")
public class EventController {
    // Service
    private final EventService eventService;

    private final EventListService eventListService;

    @Operation(summary = "행사 대시보드", description = "행사리스트 검색 결과 제공 - 대시보드")
    @GetMapping("/dashboard")
    public C2VResponse findEventForDashBoard(EventListRequest req) throws Exception {
        return eventService.findEventForDashBoard(req);
    }

    @GetMapping("/language")
    public C2VResponse language() {
        List<CodeDto> languageList = eventService.languageList();

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(languageList)
                .build();
    }

    @Operation(summary = "행사 리스트", description = "행사,삭제 리스트 검색 결과 제공")
    @GetMapping
    public C2VResponse findEvents(EventListRequest req) throws Exception {
        //return eventService.findEvents(req);
        return eventListService.findEvents(req);
    }

    @Operation(summary = "행사 리스트 (팝업)", description = "팝업 행사 리스트")
    @GetMapping("/pop")
    public C2VResponse findEventsForPop(EventListRequest req) throws Exception {
        return eventService.findEventsForPop(req);
    }

    @Operation(summary = "행사 시간표 (팝업)", description = "팝업 시간표")
    @GetMapping("/{eventId}/timeline")
    public C2VResponse findEventsForPop(@PathVariable("eventId") long eventId) throws Exception {
        return eventService.findEventForTimeline(eventId);
    }

    @GetMapping("/{eventId}")
    public C2VResponse get(@PathVariable("eventId") long eventId) throws Exception {
        EventGetResponse eventGetResponse = eventService.get(eventId);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(eventGetResponse)
                .build();
    }

    @GetMapping("/detailPage/{eventId}")
    public C2VResponse getDetailPage(@PathVariable("eventId") long eventId) throws  Exception {
        EventDetailGetResponse detailPageResponse = eventService.getDetailPage(eventId);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(detailPageResponse)
                .build();
    }

    @PostMapping
    public C2VResponse create(@Valid @RequestBody EventCreateRequest req,
                              HttpServletResponse response) throws Exception {
        EventCreateResponse eventCreateResponse = eventService.create(req);

        if (response != null)
            response.setStatus(HttpStatus.CREATED.value());

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(eventCreateResponse)
                .build();
    }

    @PatchMapping("/{eventId}/ticket-sell/status")
    public C2VResponse updateEmergencyTicketStatus(@PathVariable("eventId") long eventId,
                                                   @RequestBody EventPatchTicketStatusRequest req) throws Exception {
        eventService.updateEmergencyTicketStateCode(req, eventId);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @PatchMapping("/{eventId}/mobile-display/status")
    public C2VResponse updateMobileDisplayStatus(@PathVariable("eventId") long eventId,
                                                 @RequestBody EventPatchMobileDisplayStatusRequest req) throws Exception {
        eventService.updateDisplayStateCode(req, eventId);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @PutMapping("/{eventId}")
    public C2VResponse update(@Valid @RequestBody EventUpdateRequest req,
                              @PathVariable("eventId") long eventId) throws Exception {
        eventService.update(req, eventId);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @DeleteMapping("/{eventId}")
    public C2VResponse delete(@PathVariable("eventId") long eventId) throws Exception {
        eventService.delete(eventId);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    /* @GetMapping("/hosts")
    public C2VResponse list(@PageableDefault(size = 10) Pageable pageable) {
        Page<EventHostEntity> result = eventHostService.list(pageable);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(result)
                .build();
    }*/


    @Operation(summary = "행사 리스트 - 공간관리", description = "행사,삭제 리스트 검색 결과 제공")
    @GetMapping("/space")
    public C2VResponse findEventForManageSpace(EventListForManageSpaceRequest req) throws Exception {
        C2VResponse result = eventService.findEventForManageSpace(req);
        return result;
    }
}
