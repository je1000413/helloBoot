package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.ResponseCode;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.EventStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.lounge.LoungeNameDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LoungeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventShiftListEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.*;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event.EventCustomRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event.EventRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LoungeService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.SurveyService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.EventHostService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.EventShiftService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen.ScreenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventShiftServiceImpl implements EventShiftService {
    // Service
    private final LanguageService languageService;
    private final EventHostService eventHostService;
    private final SurveyService surveyService;
    private final ScreenService screenService;
    private final LoungeService loungeService;

    // Repository
    private final EventRepository eventRepository;
    private final EventCustomRepository eventCustomRepository;
    private static final String PREFIX = "e";

    @Override
    public EventGetResponse get(long eventId) throws C2VException {
        EventEntity ee = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("eventEntity does not exist eventId: %s", eventId)));

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
        Map<LanguageType, Boolean> languageTypeMap = new EnumMap<>(LanguageType.class);

        for (LanguageType type: LanguageType.values()) {
            languageTypeMap.put(type, languageTypes.contains(type));
        }

        //
        EventHostGetResponse eventHostInfo = eventHostService.getEventHostResponse(ee.getEventHosts());

        // survey
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
    public EventCreateResponse create(EventShiftCreateRequest req) throws Exception {
        String seq = SequenceUtils.getSequence();

        long eventId = (req.getEventId() <= 0) ? SequenceUtils.getSequenceConvention(SequenceUtils.ConventionType.EVENT) : req.getEventId();

        EventEntity ee = EventEntity
                .dataBuilder(req)
                .eventId(eventId)
                .offlineLocation(String.format("offline_location_%s", seq))
                .offlineDetailLocation(String.format("offline_detail_location_%s", seq))
                .eventName(String.format("event_name_%s", seq))
                .eventDescription(String.format("event_description_%s", seq))
                .build();

        Map<String, Map<LanguageType,String>> messageMap = makeLanguageHashMap(req, seq);
        List<LanguageEntity> result = languageService.makeLanguageEntities(messageMap);

        if (!result.isEmpty())
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
    public void update(EventShiftUpdateRequest req, long eventId) throws Exception {
        EventEntity ee = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("eventId does not exist eventId: %s", eventId)));

        // 임시 저장 행사 update
        ee.update(req);

        languageService.updateLanguageEntityList(req.getEventName(), ee.getEventName());
        languageService.updateLanguageEntityList(req.getEventDescription(), ee.getEventDescription());
        languageService.updateLanguageEntityList(req.getOfflineLocation(), ee.getOfflineLocation());
        languageService.updateLanguageEntityList(req.getOfflineDetailLocation(), ee.getOfflineDetailLocation());

        // 주최사 업데이트
        eventHostService.update(req.getEventHosts(), ee.getEventId());

        // 설문 업데이트
        surveyService.update(req.getSurveys(), eventId, SurveyMappingType.EVENT);

        // 행사 커버 이미지 업데이트
        screenService.update(req.getEventCoverImages(), eventId, ScreenMappingType.EVENT);

        // 행사 팝업 배너 업데이트
        screenService.update(req.getEventPopupBanners(), eventId, ScreenMappingType.EVENT);

        // 행사 상세 정보 업데이트
        screenService.update(req.getEventDetailPage(), eventId, ScreenMappingType.EVENT);
    }

    @Override
    @Transactional
    public void migration(long eventId) {
        EventEntity ee = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("eventId does not exist eventId: %s", eventId)));

        ee.migration();
    }

    @Override
    @Transactional
    public void delete(long eventId) throws C2VException {
        EventEntity ee = findByEventId(eventId);

        // 이미 삭제 된 대상은 그냥 pass
        // 삭제 기준은 확인이 필요
        if (ee.getStateCode() == EventStateCode.DELETE &&
                ee.getStateCode() == EventStateCode.OPENED) {
            ee.delete();
        }
    }

    private EventEntity findByEventId(long eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("eventEntity does not exist eventId: %s", eventId)));
    }

    private Map<String, Map<LanguageType,String>> makeLanguageHashMap(EventShiftCreateRequest req, String seq) {
        Map<String, Map<LanguageType,String>> languageMap = new HashMap<>();

        Map<LanguageType, String> eventNameMap = Objects.requireNonNullElseGet(req.getEventName(), HashMap::new);
        Map<LanguageType, String> eventDescriptionMap = Objects.requireNonNullElseGet(req.getEventDescription(), HashMap::new);
        Map<LanguageType, String> offlineLocationMap = Objects.requireNonNullElseGet(req.getOfflineLocation(), HashMap::new);
        Map<LanguageType, String> offlineDetailLocationMap = Objects.requireNonNullElseGet(req.getOfflineDetailLocation(), HashMap::new);

        if (eventNameMap.isEmpty())
            eventNameMap.put(LanguageType.KO_KR, "");

        if (eventDescriptionMap.isEmpty())
            eventDescriptionMap.put(LanguageType.KO_KR, "");

        if (offlineLocationMap.isEmpty())
            offlineLocationMap.put(LanguageType.KO_KR, "");

        if (offlineDetailLocationMap.isEmpty())
            offlineDetailLocationMap.put(LanguageType.KO_KR, "");

        languageMap.put(String.format("event_name_%s", seq), eventNameMap);
        languageMap.put(String.format("event_description_%s", seq), eventDescriptionMap);
        languageMap.put(String.format("offline_location_%s", seq), offlineLocationMap);
        languageMap.put(String.format("offline_detail_location_%s", seq), offlineDetailLocationMap);

        return languageMap;
    }

    private Map<LanguageType, String> makeLoungeNameMap() {
        Map<LanguageType, String> enumMap = new EnumMap<>(LanguageType.class);

        for (LanguageType value : LanguageType.values()) {
            enumMap.putIfAbsent(value, "");
        }

        return enumMap;
    }


    @Override
    @Transactional
    public C2VResponse findEventForShift(EventListRequest req) throws C2VException {

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

        List<EventShiftListEntity> list = eventCustomRepository.findEventForShift(params);

        var data = new HashMap<String, Object>();
        data.put("totalCount", list.size() > 0 ? list.get(0).getTotalCount() : 0);
        data.put("list", list);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }



}
