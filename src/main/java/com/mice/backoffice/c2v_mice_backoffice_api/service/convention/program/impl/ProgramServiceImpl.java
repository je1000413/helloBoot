package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.program.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RedisCommandUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.EventStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.MakeLanguageDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramListEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program.*;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.ProgramRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.program.ProgramCustomRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.EventService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.program.ProgramService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen.ScreenService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.SurveyService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.RedisPubService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    //service
    private final EventService eventService;
    private final SessionService sessionService;
    private final LanguageService languageService;
    private final SurveyService surveyService;
    private final ScreenService screenService;
    private final RedisPubService redisPubService;

    //repository
    private final ProgramRepository programRepository;
    private final ProgramCustomRepository programCustomRepository;

    private static final String PREFIX = "p";

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional
    public ProgramGetResponse get(long programId) throws Exception {
        ProgramEntity pe = findByProgramId(programId);

        if (Character.toLowerCase(pe.getUseYn()) == 'n') {
            throw new C2VException(ResponseCode.INTERNAL_SERVER_ERROR, String.format("deleted program programId: %s", programId));
        }

        Map<LanguageType, String> programNameMap = languageService.makeLanguageMap(pe.getProgramName());
        Map<LanguageType, String> programDescriptionMap = languageService.makeLanguageMap(pe.getProgramDescription());
        Map<LanguageType, String> eventNameMap = languageService.makeLanguageMap(pe.getEvent().getEventName());

        Set<LanguageType> languageTypes = programDescriptionMap.keySet();
        Map<LanguageType, Boolean> languageTypeMap = Arrays.stream(LanguageType.values()).collect(Collectors.toMap(x -> x, languageTypes::contains));

        // 설문
        List<SurveyGetResponse> surveyList = surveyService.listBySurveyMappingId(programId);

        // screen
        List<ScreenGetResponse> programCoverImageList = screenService.list(pe.getScreens());

        // 세션
        List<SessionGetResponse> sessionList = sessionService.list(pe.getSessions());

        return ProgramGetResponse
                .dataBuilder(pe)
                .languageTypeMap(languageTypeMap)
                .programName(programNameMap)
                .programDescription(programDescriptionMap)
                .eventName(eventNameMap)
                .surveys(surveyList)
                .sessions(sessionList)
                .programCoverImages(programCoverImageList)
                .build();
    }

    @Override
    @Transactional
    public ProgramEntity findByProgramId(long programId) throws Exception {
        return programRepository
                .findById(programId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("programEntity does not exist programId: %s", programId)));
    }

    @Override
    @Transactional
    public ProgramCreateResponse create(ProgramCreateRequest req) throws Exception {
        EventEntity ee = eventService.findByEventId(req.getEventId());

        String seq = SequenceUtils.getSequence();

        Map<String, Map<LanguageType, String>> messageMap = makeLanguageHashMap(seq, req);
        List<LanguageEntity> result = languageService.makeLanguageEntities(messageMap);

        languageService.saveAll(result);

        ProgramEntity pe = ProgramEntity
                .dataBuilder(req)
                .event(ee)
                .programName(String.format("program_name_%s" ,seq))
                .programDescription(String.format("program_description_%s", seq))
                .build();

        programRepository.save(pe);

        boolean contains = em.contains(pe);

        // 설문 저장
        surveyService.create(req.getSurveys(), pe.getProgramId(), SurveyMappingType.PROGRAM);

        // screen 생성
        screenService.create(req.getProgramCoverImages(), pe.getProgramId(), ScreenMappingType.PROGRAM);

        // 세션 저장
        sessionService.create(req.getSessions(), pe);

        return ProgramCreateResponse
                .builder()
                .programId(pe.getProgramId())
                .build();
    }

    @Override
    @Transactional
    public void update(long programId, ProgramUpdateRequest req) throws Exception {
        ProgramEntity pe = findByProgramId(programId);

        if (Character.toLowerCase(pe.getUseYn()) == 'n') {
            throw new C2VException(ResponseCode.INTERNAL_SERVER_ERROR, String.format("deleted program programId: %s", programId));
        }

        EventEntity ee = eventService.findByEventId(req.getEventId());

        if (ee.getStateCode() == EventStateCode.DELETE) {
            throw new C2VException(ResponseCode.INTERNAL_SERVER_ERROR, String.format("deleted event eventId: %s", req.getEventId()));
        }

        // 프로그램 다국어 업데이트
        languageService.updateLanguageEntityList(req.getProgramDescription(), pe.getProgramDescription());
        languageService.updateLanguageEntityList(req.getProgramName(), pe.getProgramName());

        // 프로그램 업데이트
        pe.update(req, ee);

        // 설문 업데이트
        surveyService.update(req.getSurveys(), programId, SurveyMappingType.PROGRAM);

        // 프로그램 커버 이미지 업데이트
        screenService.update(req.getProgramCoverImages(), pe.getProgramId(), ScreenMappingType.PROGRAM);

        //세션 업데이트
        sessionService.update(req.getSessions(), pe);

        // mice redis publish
        publishEventDataChange(req.getEventId());
    }

    @Override
    @Transactional
    public void delete(long programId) throws Exception {
        ProgramEntity pe = findByProgramId(programId);

        pe.delete();
    }

    private Map<String, Map<LanguageType, String>> makeLanguageHashMap(String seq, ProgramCreateRequest pr) {
        HashMap<String, Map<LanguageType, String>> languageMap = new HashMap<>();

        languageMap.put(String.format("program_name_%s", seq), pr.getProgramName());
        languageMap.put(String.format("program_description_%s", seq), pr.getProgramDescription());

        return languageMap;
    }


    public C2VResponse findProgramForList(ProgramListRequest req) throws C2VException {
        Map<String, Object> params                  = new LinkedHashMap<>();
        params.put("in_cur_admin_id"                , RequestUtils.getCurAdminId());
        params.put("in_search_date_type"            , req.getSearchDateType());
        params.put("in_search_date_s_datetime"      , req.getSearchDateSDatetime());
        params.put("in_search_date_e_datetime"      , req.getSearchDateEDatetime());
        params.put("in_search_program_type_code"    , req.getSearchProgramTypeCode());
        params.put("in_search_program_type"         , req.getSearchProgramType());
        params.put("in_search_program_value"        , req.getSearchProgramValue());
        params.put("in_search_domain_type"          , req.getSearchDomainType());
        params.put("in_search_domain_value"         , req.getSearchDomainValue());
        params.put("in_page_size"                   , req.getPageSize());
        params.put("in_page_num"                    , req.getPageNum());

        List<ProgramListEntity> list = programCustomRepository.findProgramForList(params);

        var data = new HashMap<String, Object>();
        data.put("totalCount", list.size() > 0 ? list.get(0).getTotalCount() : 0);
        data.put("list", list);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }

    private void publishEventDataChange(long eventId) {
        String command = RedisCommandUtils.EVENT_DATA_CHANGE;
        String commandKey = RedisPubService.COMMAND_KEY + SequenceUtils.getSequence();

        Map<String, Object> commandMap = new HashMap<>();

        commandMap.put("command", command);
        commandMap.put("commandKey", commandKey);
        commandMap.put("eventId", eventId);

        redisPubService.publish(commandMap);
    }
}
