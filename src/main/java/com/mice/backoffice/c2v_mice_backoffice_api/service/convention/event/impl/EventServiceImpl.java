package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils.ConventionType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports.AdminLogActionCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports.AdminLogMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.EventStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.OperatorSupports.CommandType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.*;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.AdminLogCreateDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.CodeDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.lounge.LoungeNameDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity.LanguageId;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LoungeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.*;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.*;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event.EventCustomRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event.EventRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.admin.AdminLogService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LoungeService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.SurveyService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.EventHostService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.EventService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen.ScreenService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.RedisPubService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    // Service
    private final LanguageService languageService;
    private final EventHostService eventHostService;
    private final SurveyService surveyService;
    private final ScreenService screenService;
    private final LoungeService loungeService;
    private final AdminLogService adminLogService;
    private final RedisPubService redisPubService;

    // Repository
    private final EventRepository eventRepository;
    private final EventCustomRepository eventCustomRepository;

    // Config
    private final Map<String, List<CodeDto>> codeConfigMap;

    private static final String PREFIX = "e";

    @Override
    @Transactional
    public EventGetResponse get(long eventId) throws Exception {
        EventEntity ee = findByEventId(eventId);

        if (ee.getStateCode() == EventStateCode.DELETE) {
            throw new C2VException(ResponseCode.INTERNAL_SERVER_ERROR, String.format("deleted event eventId: %s", eventId));
        }

        List<LoungeEntity> loungeEntityList = loungeService.findAllByEventId(eventId);
        List<LoungeNameDto> loungeInfoList = new ArrayList<>();

        Map<LanguageType, String> nameMap = languageService.makeLanguageMap(ee.getEventName());
        Map<LanguageType, String> descriptionMap = languageService.makeLanguageMap(ee.getEventDescription());
        Map<LanguageType, String> offlineLocationMap = languageService.makeLanguageMap(ee.getOfflineLocation());
        Map<LanguageType, String> offlineDetailLocationMap = languageService.makeLanguageMap(ee.getOfflineDetailLocation());

        loungeEntityList.forEach(x -> {
            Map<LanguageType, String> loungeNameMap = Objects.isNull(x) ? new EnumMap<>(LanguageType.class) : languageService.makeLanguageMap(x.getLoungeName());
            LoungeNameDto dto = LoungeNameDto
                    .builder()
                    .type(x.getLoungeType())
                    .stateCode(x.getStateCode())
                    .name(loungeNameMap)
                    .build();

            loungeInfoList.add(dto);
        });

        Set<LanguageType> languageTypes = nameMap.keySet();
        Map<LanguageType, Boolean> languageTypeMap = Arrays.stream(LanguageType.values()).collect(Collectors.toMap(x -> x, languageTypes::contains));

        // 주최사 정보
        EventHostGetResponse eventHostInfo = eventHostService.getEventHostResponse(ee.getEventHosts());

        // 설문
        List<SurveyGetResponse> surveyList = surveyService.listBySurveyMappingId(eventId);

        List<ScreenGetResponse> screenList = screenService.list(ee.getScreens());
        Map<DisplayCode, List<ScreenGetResponse>> displayCodeListMap = screenList
                .stream()
                .collect(Collectors
                        .groupingBy(ScreenGetResponse::getDisplayCode));

        return EventGetResponse
                .dataBuilder(ee)
                .eventHostInfo(eventHostInfo)
                .languageTypeMap(languageTypeMap)
                .surveys(surveyList)
                .eventCoverImages(displayCodeListMap.getOrDefault(DisplayCode.COVER_IMAGE, new ArrayList<>()))
                .eventPopupBanners(displayCodeListMap.getOrDefault(DisplayCode.POPUP_BANNER, new ArrayList<>()))
                .eventDetailPage(displayCodeListMap.getOrDefault(DisplayCode.DETAIL_PAGE, new ArrayList<>())
                        .stream()
                        .findFirst()
                        .orElse(ScreenGetResponse
                                .builder()
                                .build()))
                .eventName(nameMap)
                .eventDescription(descriptionMap)
                .offlineLocation(offlineLocationMap)
                .offlineDetailLocation(offlineDetailLocationMap)
                .loungeInfos(loungeInfoList)
                .build()
                .init();
    }

    @Override
    @Transactional
    public EventDetailGetResponse getDetailPage(long eventId) throws Exception {
        EventEntity ee = findByEventId(eventId);

        if (ee.getStateCode() == EventStateCode.DELETE) {
            throw new C2VException(ResponseCode.INTERNAL_SERVER_ERROR, String.format("deleted event eventId: %s", eventId));
        }

        ScreenEntity se = ee
                .getScreens()
                .stream()
                .filter(x -> x.getStateCode() != ScreenStateCode.DELETE
                        && x.getDisplayCode() == DisplayCode.DETAIL_PAGE)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("screen entity does not exist"));

        ScreenDisplayEntity sde = se
                .getScreenDisplays()
                .stream()
                .filter(x -> x.getStateCode() != Supports.ScreenDisplayStateCode.DELETE
                        && x.getDisplayType() == DisplayType.HTML)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("screenDisplay entity does not exist"));

        LanguageType languageType = LanguageType.valueOf(RequestUtils.getCurLangCd().toUpperCase());

        LanguageId languageId = LanguageId
                .builder()
                .languageCode(sde.getDisplayContents())
                .languageType(languageType)
                .build();

        LanguageEntity le = languageService.findByLanguageId(languageId);

        return EventDetailGetResponse
                .builder()
                .html(le.getMessage())
                .build();
    }

    @Override
    @Transactional
    public EventEntity findByEventId(long eventId) throws Exception {
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("eventEntity does not exist eventId: %s", eventId)));
    }

    @Override
    @Transactional
    public EventCreateResponse create(EventCreateRequest req) throws Exception {
        if (!isValidDate(req))
            throw new C2VException(ResponseCode.INTERNAL_SERVER_ERROR , "Date is invalid.");

        String seq = SequenceUtils.getSequence();

        long eventId = (req.getEventId() <= 0) ? SequenceUtils.getSequenceConvention(ConventionType.EVENT) : req.getEventId();

        EventEntity ee = EventEntity
                .dataBuilder(req)
                .eventId(eventId)
                .offlineLocation(String.format("offline_location_%s", seq))
                .eventName(String.format("event_name_%s", seq))
                .eventDescription(String.format("event_description_%s", seq))
                .offlineDetailLocation(String.format("offline_detail_location_%s", seq))
                .build();

        Map<String, Map<LanguageType,String>> messageMap = makeLanguageHashMap(req, seq);
        List<LanguageEntity> result = languageService.makeLanguageEntities(messageMap);

        languageService.saveAll(result);

        // 이벤트 저장
        eventRepository.save(ee);
        // 이벤트 호스트 저장
        eventHostService.create(req.getEventHosts(), ee.getEventId());
        // 설문 저장
        surveyService.create(req.getSurveys(), ee.getEventId(), SurveyMappingType.EVENT);
        // 행사 커버 & 행사 정보 팝업 배너 저장
        screenService.create(req.getEventCoverImages(), ee.getEventId(), ScreenMappingType.EVENT);
        screenService.create(req.getEventPopupBanners(), ee.getEventId(), ScreenMappingType.EVENT);

        // 행사 상세 페이지 저장
        screenService.create(req.getEventDetailPage(), ee.getEventId(), ScreenMappingType.EVENT);

        return EventCreateResponse
                .builder()
                .eventId(ee.getEventId())
                .build();
    }

    @Override
    @Transactional
    public void updateDisplayStateCode(EventPatchMobileDisplayStatusRequest req, long eventId) throws Exception {
        EventEntity ee = findByEventId(eventId);
        ee.updateDisplayStateCode(req);

        AdminLogCreateDto dto = AdminLogCreateDto
                .builder()
                .actionCode(AdminLogActionCode.EVENT_DISPLAY_STATE)
                .mappingType(AdminLogMappingType.EVENT)
                .message(String.format("display_state_code: %s", req.getMobileDisplayStatus()))
                .mappingId(Long.toString(ee.getEventId()))
                .build();

        adminLogService.create(dto);

    }

    @Override
    @Transactional
    public void updateEmergencyTicketStateCode(EventPatchTicketStatusRequest req, long eventId) throws Exception {
        EventEntity ee = findByEventId(eventId);
        ee.updateEmergencyTicketStateCode(req);

        AdminLogCreateDto dto = AdminLogCreateDto
                .builder()
                .actionCode(AdminLogActionCode.EVENT_SELL_EMERGENCY_STATE)
                .mappingType(AdminLogMappingType.EVENT)
                .message(String.format("sell_state_code: %s", req.getSellStateCode()))
                .mappingId(Long.toString(ee.getEventId()))
                .build();

        adminLogService.create(dto);
    }

    @Override
    @Transactional
    public void update(EventUpdateRequest req, long eventId) throws Exception {
        EventEntity ee = findByEventId(eventId);

        // 행사 update
        ee.update(req);

        // 다국어 컬럼 업데이트
        languageService.updateLanguageEntityList(req.getEventName(), ee.getEventName());
        languageService.updateLanguageEntityList(req.getEventDescription(), ee.getEventDescription());
        languageService.updateLanguageEntityList(req.getOfflineLocation(), ee.getOfflineLocation());
        languageService.updateLanguageEntityList(req.getOfflineDetailLocation(), ee.getOfflineDetailLocation());

        // 주최사 업데이트
        eventHostService.update(req.getEventHosts(), ee.getEventId());

        // 설문 업데이트
        if (req.getSurveys() != null && !req.getSurveys().isEmpty())
            surveyService.update(req.getSurveys(), eventId, SurveyMappingType.EVENT);

        // 행사 커버 이미지 업데이트
        if (req.getEventCoverImages() != null && !req.getEventCoverImages().isEmpty())
            screenService.update(req.getEventCoverImages(), eventId, ScreenMappingType.EVENT);

        // 행사 팝업 배너 업데이트
        if (req.getEventPopupBanners() != null && !req.getEventPopupBanners().isEmpty())
            screenService.update(req.getEventPopupBanners(), eventId, ScreenMappingType.EVENT);

        // 행사 상세 정보 업데이트
        if (req.getEventDetailPage() != null)
            screenService.update(req.getEventDetailPage(), eventId, ScreenMappingType.EVENT);

        // mice redis publish
        publishEventDataChange(eventId);
    }

    @Override
    @Transactional
    public void delete(long eventId) throws Exception {
        EventEntity ee = findByEventId(eventId);

        // 이미 삭제 된 대상은 그냥 pass
        // 삭제 기준 행사 시작 시간 전이면 삭제 가능 <- 신지웅 차석님 확인 04.20일
        if (ee.getStateCode() != EventStateCode.DELETE &&
                ee.getStartDatetime().isAfter(LocalDateTime.now())) {
                ee.delete();
        }
    }

    private Map<String, Map<LanguageType,String>> makeLanguageHashMap(EventCreateRequest req, String seq) {
        Map<String, Map<LanguageType,String>> languageMap = new HashMap<>();

        languageMap.put(String.format("event_name_%s", seq), req.getEventName());
        languageMap.put(String.format("event_description_%s", seq), req.getEventDescription());
        languageMap.put(String.format("offline_location_%s", seq), req.getOfflineLocation());
        languageMap.put(String.format("offline_detail_location_%s", seq), req.getOfflineDetailLocation());

        return languageMap;
    }

    @Override
    public List<CodeDto> languageList() {
        return codeConfigMap.getOrDefault("LANGUAGE_TYPE", Collections.<CodeDto>emptyList());
    }

    @Override
    @Transactional
    public C2VResponse findEventForDashBoard(EventListRequest req) throws C2VException {

        Map<String, Object> params              = new LinkedHashMap<>();
        params.put("in_cur_admin_id"            , RequestUtils.getCurAdminId());
        params.put("in_search_date_type"        , req.getSearchDateType());
        params.put("in_search_date_s_datetime"  , req.getSearchDateSDatetime());
        params.put("in_search_date_e_datetime"  , req.getSearchDateEDatetime());
        params.put("in_search_status_type"      , req.getSearchStatusType());
        params.put("in_search_event_type"       , req.getSearchEventType());
        params.put("in_search_event_value"      , req.getSearchEventValue());
        params.put("in_search_domain_type"      , req.getSearchDomainType());
        params.put("in_search_domain_value"     , req.getSearchDomainValue());
        params.put("in_page_size"               , req.getPageSize());
        params.put("in_page_num"                , req.getPageNum());
        params.put("in_lounge_yn"               , req.getLoungeYn());

        List<EventListForDashBoardEntity> list = eventCustomRepository.findEventForDashBoard(params);

        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", list.size() > 0 ? list.get(0).getTotalCount() : 0);
        data.put("list", list);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }

    @Override
    @Transactional
    public C2VResponse findEvents(EventListRequest req) throws C2VException {

        Map<String, Object> params              = new LinkedHashMap<>();
        params.put("in_cur_admin_id"            , RequestUtils.getCurAdminId());
        params.put("in_search_date_type"        , req.getSearchDateType());
        params.put("in_search_date_s_datetime"  , req.getSearchDateSDatetime());
        params.put("in_search_date_e_datetime"  , req.getSearchDateEDatetime());
        params.put("in_search_status_type"      , req.getSearchStatusType());
        params.put("in_search_event_type"       , req.getSearchEventType());
        params.put("in_search_event_value"      , req.getSearchEventValue());
        params.put("in_search_domain_type"      , req.getSearchDomainType());
        params.put("in_search_domain_value"     , req.getSearchDomainValue());
        params.put("in_page_size"               , req.getPageSize());
        params.put("in_page_num"                , req.getPageNum());
        params.put("in_lounge_yn"               , req.getLoungeYn());

        List<EventListEntity> list = eventCustomRepository.findEventForList(params);

        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", list.size() > 0 ? list.get(0).getTotalCount() : 0);
        data.put("list", list);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }

    @Override
    @Transactional
    public C2VResponse findEventsForPop(EventListRequest req) throws C2VException {

        Map<String, Object> params              = new LinkedHashMap<>();
        params.put("in_cur_admin_id"            , RequestUtils.getCurAdminId());
        params.put("in_search_date_type"        , req.getSearchDateType());
        params.put("in_search_date_s_datetime"  , req.getSearchDateSDatetime());
        params.put("in_search_date_e_datetime"  , req.getSearchDateEDatetime());
        params.put("in_search_status_type"      , req.getSearchStatusType());
        params.put("in_search_event_type"       , req.getSearchEventType());
        params.put("in_search_event_value"      , req.getSearchEventValue());
        params.put("in_search_domain_type"      , req.getSearchDomainType());
        params.put("in_search_domain_value"     , req.getSearchDomainValue());
        params.put("in_page_size"               , req.getPageSize());
        params.put("in_page_num"                , req.getPageNum());
        params.put("in_lounge_yn"                , req.getLoungeYn());

        List<EventListEntity> list = eventCustomRepository.findEventForList(params);

        var responseData = list.stream().map(evt -> {

            Map<String, Boolean> language = new HashMap<String, Boolean>();
            language.put("ko_kr", evt.getIsKoKr() > 0);
            language.put("en_us", evt.getIsEnUs() > 0);

            return EventListForPopResponse.builder()
                    .rowNum(evt.getRowNum())
                    .eventId(evt.getEventId())
                    .eventName(evt.getEventName())
                    .eventStartDatetime(evt.getEventStartDatetime())
                    .eventEndDatetime(evt.getEventEndDatetime())
                    .eventHosts(evt.getEventHosts())
                    .stateCode(evt.getStateCode())
                    .stateName(evt.getStateName())
                    .language(language)
                    .loungeNo(evt.getLoungeNo())
                    .build();
        }).collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("totalCount", list.size() > 0 ? list.get(0).getTotalCount() : 0);
        data.put("list", responseData);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }

    @Override
    @Transactional
    public C2VResponse findEventForTimeline(long eventId) throws C2VException {
        Map<String, Object> params              = new LinkedHashMap<>();
        params.put("in_cur_admin_id"            , RequestUtils.getCurAdminId());
        params.put("in_event_id"                , eventId);

        List<EventListForTimelineEntity> list = eventCustomRepository.findEventForTimeline(params);

        if(list.size() < 1)
            throw new C2VException(ResponseCode.OK, "조회된 데이터가 없습니다.");


        AtomicInteger index = new AtomicInteger();

        EventListForTimelineEntity rootEntity = list.get(0);
        EventListForTimelineResponse response = EventListForTimelineResponse.builder()
                .eventId(rootEntity.getEventId())
                .eventName(rootEntity.getEventName())
                .eventStartDatetime(rootEntity.getEventStartDatetime())
                .eventEndDatetime(rootEntity.getEventEndDatetime())
                .eventDays(
                    list.stream()
                        .map(evt -> evt.getEventDay())
                        .distinct()
                        .sorted()
                        .map(d -> EventListForTimelineResponse.EventDay.builder()
                                .eventDay(d)
                                .eventSort(index.getAndIncrement()+1)
                                .eventPrograms(
                                        list.stream()
                                                .filter(p -> p.getEventDay().equals(d))
                                                .map(p -> p.getProgramId())
                                                .distinct()
                                                .map(p -> {
                                                    EventListForTimelineEntity entity = list.stream().filter(e-> e.getEventDay().equals(d) && e.getProgramId().equals(p)).findFirst().get();
                                                    return EventListForTimelineResponse.EventProgram.builder()
                                                            .ProgramId(p)
                                                            .ProgramName(entity.getProgramName())
                                                            .ProgramTypeName(entity.getProgramTypeName())
                                                            .eventProgramSessions(
                                                                    list.stream()
                                                                            .filter(s -> s.getProgramId().equals(p))
                                                                            .map(s -> EventListForTimelineResponse.EventProgramSession.builder()
                                                                                    .SessionId(s.getSessionId())
                                                                                    .SessionName(s.getSessionName())
                                                                                    .staffName(s.getStaffName())
                                                                                    .hallName(s.getHallName())
                                                                                    .sessionStartTimeline(s.getSessionStartTimeline())
                                                                                    .sessionEndTimeline(s.getSessionEndTimeline())
                                                                                    .sessionMinDiff(s.getSessionNinDiff())
                                                                                    .build())
                                                                            .toList()
                                                            ).build();
                                                }).toList()
                                ).build()
                        ).toList()
                ).build();

//
//        /* 이벤트 Day 바인드 */
//        response.setEventDays(
//                list.stream()
//                        .map(evt -> evt.getEventDay())
//                        .distinct()
//                        .sorted()
//                        .map(d -> EventListForTimelineResponse.EventDay.builder().eventDay(d).build()).toList());
//
//        /* 프로그램 바인드 */
//        IntStream.range(0, response.getEventDays().size()).forEach(idx -> {
//
//            var d = response.getEventDays().get(idx);
//                d.setEventSort(idx+1);
//                d.setEventPrograms(
//                        list.stream()
//                                .filter(p -> p.getEventDay().equals(d.getEventDay()))
//                                .map(p -> p.getProgramId())
//                                .distinct()
//                                .map(p -> {
//                                    EventListForTimelineEntity entity = list.stream().filter(e-> e.getEventDay().equals(d.getEventDay()) && e.getProgramId().equals(p)).findFirst().get();
//                                    return EventListForTimelineResponse.EventProgram.builder().ProgramId(p).ProgramName(entity.getProgramName()).ProgramTypeName(entity.getProgramTypeName()).build();
//                                }).toList()
//                );
//
//                /* 세션 바인드 */
//                d.getEventPrograms().forEach(p -> {
//                    p.setEventProgramSessions(
//                            list.stream()
//                                    .filter(s -> s.getProgramId().equals(p.getProgramId()))
//                                    .map(s -> EventListForTimelineResponse.EventProgramSession.builder()
//                                            .SessionId(s.getSessionId())
//                                            .SessionName(s.getSessionName())
//                                            .staffName(s.getStaffName())
//                                            .hallName(s.getHallName())
//                                            .sessionStartTimeline(s.getSessionStartTimeline())
//                                            .sessionEndTimeline(s.getSessionEndTimeline())
//                                            .sessionMinDiff(s.getSessionNinDiff())
//                                            .build())
//                                    .toList()
//                    );
//                });
//            });

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(response).build();
    }

    private boolean isValidDate(EventCreateRequest ecr) {
        // endDatetime <= startDatetime
        // loungeEndDatetime <= loungeStartDatetime
        // displayEndDatetime <= displayStartDatetime

        return ecr.getEndDatetime().compareTo(ecr.getStartDatetime()) >= 0 &&
                ecr.getDisplayEndDatetime().compareTo(ecr.getDisplayStartDatetime()) >= 0;
    }

    private void publishEventDataChange(long eventId) {
        String command = CommandType.EventDataChange.getValue();
        String commandKey = RedisPubService.COMMAND_KEY + SequenceUtils.getSequence();

        Map<String, Object> commandMap = new HashMap<>();

        commandMap.put("command", command);
        commandMap.put("commandKey", commandKey);
        commandMap.put("eventId", eventId);

        redisPubService.publish(commandMap);

        //redisPubService.putAll("putTest", commandMap);
    }

    @Override
    @Transactional
    public C2VResponse findEventForManageSpace(EventListForManageSpaceRequest req) throws C2VException {
        Map<String, Object> params              = new LinkedHashMap<>();
        params.put("in_cur_admin_id"            , RequestUtils.getCurAdminId());
        params.put("in_search_date_type"        , req.getSearchDateType());
        params.put("in_search_date_s_datetime"  , req.getSearchDateSDatetime());
        params.put("in_search_date_e_datetime"  , req.getSearchDateEDatetime());
        params.put("in_search_status_type"      , req.getSearchStatusType());
        params.put("in_search_event_type"       , req.getSearchEventType());
        params.put("in_search_event_value"      , req.getSearchEventValue());
        params.put("in_page_size"               , req.getPageSize());
        params.put("in_page_num"                , req.getPageNum());
        List<EventListForManageSpaceEntity> list = eventCustomRepository.findEventForManageSpace(params);
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(Map.of("totalCount", list.size() > 0 ? list.get(0).getTotalCount() : 0, "list", list)).build();

    }
}
