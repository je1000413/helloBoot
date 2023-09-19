package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RedisCommandUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LoungeStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LoungeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge.*;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.LoungeRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event.EventRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.screen.ScreenRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.mybatis.LoungeMybatisRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LoungeService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen.ScreenService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.RedisPubService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.system.CodeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoungeServiceImpl implements LoungeService {


    @Autowired
    private LoungeMybatisRepository loungeMybatisRepository;
    @Autowired
    private LoungeRepository loungeRepository;
    @Autowired
    private ScreenService screenService;
    @Autowired
    LanguageService languageService;
    @Autowired
    CodeService codeService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ScreenRepository screenRepository;
    @Autowired
    private final RedisPubService redisPubService;

    // jpa repository
    private final LoungeRepository repository;


    @Override
    public List<LoungeEntity> findAllByEventId(long eventId) throws C2VException {
        return repository.findAllByPkEventIdAndStateCodeNot(eventId, LoungeStateCode.DELETE);
    }

    // list에 cnt로 추가
    @Override
    public C2VResponse list(LoungeListRequest req) throws C2VException {

        LoungeListSqlRequest loungeListSqlRequest = new LoungeListSqlRequest();

        //일자별
        if(req.getDateType() != null && req.getStartDateTime() != null && req.getEndDateTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
            String startDatetime = req.getStartDateTime().format(formatter);
            String endDatetime = req.getEndDateTime().format(formatter);
            if (req.getDateType().equals(Supports.LoungeSearchDateType.CREATE_DATE)) {
                loungeListSqlRequest.setStartCreateDatetime(startDatetime);
                loungeListSqlRequest.setEndCreateDatetime(endDatetime);
            } else if (req.getDateType().equals(Supports.LoungeSearchDateType.LOUNGE_DATE)) {
                loungeListSqlRequest.setStartLoungeDatetime(startDatetime);
                loungeListSqlRequest.setEndLoungeDatetime(endDatetime);
            } else if (req.getDateType().equals(Supports.LoungeSearchDateType.UPDATE_DATE)) {
                loungeListSqlRequest.setStartUpdateDatetime(startDatetime);
                loungeListSqlRequest.setEndUpdateDatetime(endDatetime);
            }
        }
        //공간 템플릿
        if(req.getTemplateId() != null){
            loungeListSqlRequest.setTemplateId(req.getTemplateId());
        }
        //이름/코드
        if(req.getNameAndCodeType() != null && req.getNameAndCodeKeyword() != null && !"".equals(req.getNameAndCodeKeyword())){
            if(req.getNameAndCodeType().equals(Supports.LoungeSearchNameAndCodeType.EVENT_NAME)){
                loungeListSqlRequest.setEventName(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.LoungeSearchNameAndCodeType.EVENT_CODE)){
                loungeListSqlRequest.setEventId(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.LoungeSearchNameAndCodeType.LOUNGE_NAME)){
                loungeListSqlRequest.setLoungeName(req.getNameAndCodeKeyword());
            }else if(req.getNameAndCodeType().equals(Supports.LoungeSearchNameAndCodeType.LOUNGE_CODE)){
                loungeListSqlRequest.setLoungeNo(req.getNameAndCodeKeyword());
            }
        }
        //도메인
        if(req.getDomainAndCodeType() != null && req.getDomainAndCodeKeyword() != null && !"".equals(req.getNameAndCodeKeyword())){
            if(req.getDomainAndCodeType().equals(Supports.LoungeSearchDomainAndCodeType.HOST_NAME)){
                loungeListSqlRequest.setDomainName(req.getNameAndCodeKeyword());
            }else if(req.getDomainAndCodeType().equals(Supports.LoungeSearchDomainAndCodeType.HOST_NAME)){
                loungeListSqlRequest.setDomainId(req.getDomainAndCodeKeyword());
            }
        }
        //페이지
        if(req.getPageNum() != 0) loungeListSqlRequest.setPageNum(req.getPageNum());
        if(req.getPageSize() != 0) loungeListSqlRequest.setPageSize(req.getPageSize());

        List<Map<String,Object>> list = loungeMybatisRepository.findLoungeListByRequest(RequestUtils.getSqlRequest(loungeListSqlRequest));

        for(int i =0; i<list.size(); i++){
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) codeService.getSpaceTemplate("MiceLounge").getData();
            String templateId = String.valueOf(list.get(i).get("template_id"));

            List<Map<String, Object>> data = dataList.stream()
                            .filter(map -> map.containsKey("value") && templateId.equals(String.valueOf(map.get("value"))))
                    .collect(Collectors.toList());
            if(data.size()>0) list.get(i).put("template_name", data.get(0).get("name"));
        }
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("Lounge List 조회 성공").data(list).build();
    }

    @Override
    public C2VResponse get(int loungeNo) throws C2VException {

        LoungeEntity le = loungeRepository.findByPkLoungeNo(loungeNo).orElseThrow(() -> new EntityNotFoundException(String.format("does not exist loungeNo: %s", loungeNo)));
        EventEntity ee = eventRepository.findById(le.getPk().getEventId()).orElseThrow(() -> new EntityNotFoundException(String.format("does not exist eventId: %s", le.getPk().getEventId())));

        if (le.getStateCode() != LoungeStateCode.USE ) {
            throw new C2VException(ResponseCode.INTERNAL_SERVER_ERROR, String.format("deleted loungeNo: %s", loungeNo));
        }

        Map<LanguageType, String> eventNameMap = languageService.makeLanguageMap(ee.getEventName());
        Map<LanguageType, String> loungeNameMap = languageService.makeLanguageMap(le.getLoungeName());


        List<ScreenEntity> seList = screenRepository.findAllByEventId(le.getPk().getLoungeNo())
                .stream()
                .filter(screenEntity ->{
                    Supports.DisplayCode displayCode = screenEntity.getDisplayCode();
                    return displayCode.getType() >= 10200 && displayCode.getType() < 10300;
                })
                .sorted(Comparator.comparingInt(screenEntity -> screenEntity.getDisplayCode().getType()))
                .collect(Collectors.toList());
        List<ScreenGetResponse> screenList = screenService.list(seList);

        int normalLoungeCnt = loungeRepository.findByEventIdAndLoungeType(le.getPk().getEventId(), Supports.LoungeType.NORMAL).size();
        int expLoungeCnt = loungeRepository.findByEventIdAndLoungeType(le.getPk().getEventId(), Supports.LoungeType.EXPERIENCE).size();

        Object templateName = null;
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) codeService.getSpaceTemplate("MiceLounge").getData();
        List<Map<String, Object>> data = dataList.stream()
                .filter(map -> map.containsKey("value") && String.valueOf(le.getTemplateId()).equals(String.valueOf(map.get("value"))))
                .collect(Collectors.toList());
        if(data.size()>0) templateName = data.get(0).get("name");

        return C2VResponse.builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg("Lounge 상세 조회 성공")
                .data(LoungeGetResponse.dataBuilder(le, ee.getLoungeStartDatetime(), ee.getLoungeEndDatetime())
                                    .eventName(eventNameMap)
                                    .loungeName(loungeNameMap)
                                    .screenList(screenList)
                                    .normalLoungeCnt(normalLoungeCnt)
                                    .expLoungeCnt(expLoungeCnt)
                                    .templateName(templateName)
                                    .build()).build();
    }

    @Override
    public C2VResponse create(LoungeCreateRequest req) throws C2VException, NoSuchAlgorithmException, IOException {
        for(int i =0; i<req.getNormalLoungeCnt(); i++) {
            createLounge(req, Supports.LoungeType.NORMAL);
        }
        for(int i =0; i< req.getExpLoungeCnt(); i++){
            createLounge(req, Supports.LoungeType.EXPERIENCE);
        }
        return C2VResponse.builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg("Lounge 생성 성공")
                .data(null)
                .build();
    }

    public void createLounge(LoungeCreateRequest req, Supports.LoungeType loungeType) throws C2VException, NoSuchAlgorithmException, IOException {

        EventEntity ee = eventRepository.findById(req.getEventId()).orElseThrow(() -> new EntityNotFoundException(String.format("does not exist eventId: %s", req.getEventId())));
        ee.lounge(req.getStartDatetime(),req.getEndDatetime());

        LoungeEntity.LoungeId pk = LoungeEntity.LoungeId.builder().eventId(req.getEventId()).loungeNo(loungeRepository.findLoungeNo()).build();
        String seq = SequenceUtils.getSequence();

        LoungeEntity le = LoungeEntity.builder()
                .pk(pk)
                .loungeName(String.format("lounge_name_%s", seq))
                .templateId(req.getTemplateId())
                .stateCode(LoungeStateCode.USE)
                .loungeType(loungeType)
                .createUserId(RequestUtils.getCurAdminId())
                .updateUserId(RequestUtils.getCurAdminId())
                .createDatetime(LocalDateTime.now())
                .updateDatetime(LocalDateTime.now()).build();


        Map<String, Map<LanguageType,String>> languageMap = new HashMap<>();
        languageMap.put(String.format("lounge_name_%s", seq), req.getLoungeName());
        List<LanguageEntity> result = languageService.makeLanguageEntities(languageMap);

        //이벤트 저장
        eventRepository.save(ee);
        // 언어 저장
        languageService.saveAll(result);
        // 라운지 저장
        loungeRepository.save(le);
        // 스크린 저장
        screenService.create(req.getScreenList(), le.getPk().getLoungeNo(), ScreenMappingType.LOUNGE);

    }

    @Override
    public C2VResponse update(int loungeNo, LoungeUpdateRequest request) throws C2VException, NoSuchAlgorithmException, IOException {

        updateProcess(request, Supports.LoungeType.NORMAL, request.getNormalLoungeCnt());
        updateProcess(request, Supports.LoungeType.EXPERIENCE, request.getExpLoungeCnt());

        publishEventDataChange(request.getEventId());

        return C2VResponse.builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg("Lounge 수정 성공")
                .data(null)
                .build();
    }

    public void updateProcess(LoungeUpdateRequest request, Supports.LoungeType loungeType, int loungeCnt) throws NoSuchAlgorithmException, IOException, C2VException {
        List<LoungeEntity> list = loungeRepository.findByEventIdAndLoungeType(request.getEventId(), loungeType);

        //기존 라운지 수 > 신규 라운지 수
        if(list.size() > loungeCnt){
            //신규 라운지 수보다 큰 만큼 삭제
            for(int i = list.size()-1 ; i >= loungeCnt ; i--){
                deleteLounge(list.get(i).getPk().getLoungeNo());
            }
            //신규 라운지 수만큼 업데이트
            for(int i= loungeCnt-1; i >= 0; i--){
                updateLounge(list.get(i).getPk().getLoungeNo(), request);
            }
            //기존 라운지 수 <= 신규 라운지 수
        }else{
            //기존 라운지 수만큼 업데이트
            for(int i =0; i< list.size(); i++){
                updateLounge(list.get(i).getPk().getLoungeNo(), request);
            }
            LoungeCreateRequest loungeCreateRequest = new LoungeCreateRequest(request);
            //추가된 신규 라운지 수만큼 생성
            for(int i = list.size(); i<loungeCnt; i++){
                createLounge(loungeCreateRequest, loungeType);
            }
        }
    }

    public void updateLounge(int loungeNo, LoungeUpdateRequest req)  throws IOException {
        EventEntity ee = eventRepository.findById(req.getEventId()).orElseThrow(() -> new EntityNotFoundException(String.format("does not exist eventId: %s", req.getEventId())));
        ee.updateLounge(req.getStartDatetime(),req.getEndDatetime());
        LoungeEntity le = loungeRepository.findByPkLoungeNo(loungeNo).orElseThrow(() -> new EntityNotFoundException(String.format("does not exist loungeNo: %s", loungeNo)));
        le.updateLounge(req.getTemplateId());

        //이벤트 업데이트
        eventRepository.save(ee);
        //라운지 업데이트
        loungeRepository.save(le);
        //랭귀지 업데이트
        languageService.updateLanguageEntityList(req.getLoungeName(), le.getLoungeName());
        //스크린 업데이트
        screenService.updateLounge(req.getScreenList(), le.getPk().getLoungeNo(), ScreenMappingType.LOUNGE);
    }

    @Override
    public C2VResponse delete(int loungeNo) {
        List<LoungeEntity> list = loungeRepository.findListByLoungeNo(loungeNo);
        EventEntity ee = eventRepository.findById(list.get(0).getPk().getEventId()).orElseThrow(() -> new EntityNotFoundException(String.format("does not exist eventId: %s", list.get(0).getPk().getEventId())));
        ee.lounge(null,null);
        eventRepository.save(ee);
        for(int i = 0; i <list.size(); i++) deleteLounge(list.get(i).getPk().getLoungeNo());

        publishEventDataChange(ee.getEventId());
        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("Lounge 삭제 성공").build();
    }

    public void deleteLounge(int loungeNo) {
        LoungeEntity le = loungeRepository.findByPkLoungeNo(loungeNo).orElseThrow(() -> new EntityNotFoundException(String.format("does not exist loungeNo: %s", loungeNo)));
        languageService.deleteByLanguageCode(le.getLoungeName());
        screenService.deleteLounge(le.getPk().getLoungeNo());
        le.deleteLounge(LoungeStateCode.DELETE);
        loungeRepository.save(le);
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
