package com.mice.backoffice.c2v_mice_backoffice_api.service.operator.Impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mice.backoffice.c2v_mice_backoffice_api.client.AuthFeignClient;
import com.mice.backoffice.c2v_mice_backoffice_api.client.GroupFeignClient;
import com.mice.backoffice.c2v_mice_backoffice_api.common.CommonUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.OperatorSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.CsvDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.operator.*;
import com.mice.backoffice.c2v_mice_backoffice_api.model.operator.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event.EventRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.operator.OperatorRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.login.LoginService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.OperatorService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.RedisPubService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {

    private final OperatorRepository operatorRepository;

    private final EventRepository eventRepository;

    private final RedisPubService redisPubService;

    private final Map<String,List<CsvDto>> motionCodeDtoList;

    private final GroupFeignClient groupFeignClient;

    private final LoginService loginService;

    private final AuthFeignClient authFeignClient;

    private Map<String, Object> params = new LinkedHashMap<>();

    private ObjectMapper objectMapper = new ObjectMapper();

    private final CommonUtils commonUtils;

    @Value("${auth.serverName}")
    private String serverName;

    @Value("${auth.serviceName}")
    private String serviceName;

    @Override
    public C2VResponse getEventPrograms(long eventId) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_cur_lang_cd"     , RequestUtils.getCurLangCd());
        params.put("in_event_id"        , eventId);
        List<OpEventEntity> list = operatorRepository.getEventPrograms(params);

        if(list.size() < 1)
            throw new C2VException(ResponseCode.OK, "조회된 데이터가 없습니다.");

        OpEventEntity opEventEntity = list.get(0);
        OpEventProgramsResponse opEventProgramsResponse = OpEventProgramsResponse.builder().eventID(opEventEntity.getEventId()).eventName(opEventEntity.getEventName()).stateCode(opEventEntity.getStateCode()).build();
        opEventProgramsResponse.setPrograms(
                list.stream()
                        .filter(e -> e.getEventId().equals(opEventEntity.getEventId()))
                        .map(p -> OpEventProgramsResponse.Program.builder()
                                .programId(p.getProgramId())
                                .programName(p.getProgramName())
                                .programType(p.getProgramType())
                                .switchType(p.getSwitchType()).build()).toList()
        );

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(opEventProgramsResponse).build();
    }


    private Long getFirstSessionId(List<OpProgramSessionEntity> list, SessionSupports.SessionStateCode stateCode) {
        try {
            return  list.stream()
                    .filter(s -> s.getSessionId() > 0)
                    .filter(s -> s.getStateCode().equals(stateCode.getType()))
                    .findFirst()
                    .get()
                    .getSessionId();

        } catch (Exception ex){
            return 0L;
        }
    }


    @Override
    public C2VResponse getEventProgramSessions(long eventId, long programId) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_cur_lang_cd"     , RequestUtils.getCurLangCd());
        params.put("in_program_id"      , programId);

        List<OpProgramSessionEntity> list = operatorRepository.getEventProgramSessions(params);

        /* 세션키 조회 로직 */
        if(list.size() < 1)
            throw new C2VException(ResponseCode.OK, "조회된 데이터가 없습니다.");

        list.stream().forEach(s -> {
            s.setCommunication(this.getCommunicationPayload(this.getCommunicationKey(s.getSessionId().toString())));
        });

        Long sessionId = 0L;

        sessionId = getFirstSessionId(list, SessionSupports.SessionStateCode.IN_LECTURE);

        if(sessionId < 1)
            sessionId = getFirstSessionId(list, SessionSupports.SessionStateCode.ENTER);

        if(sessionId < 1)
            sessionId = getFirstSessionId(list, SessionSupports.SessionStateCode.READY);

        if(sessionId < 1)
            sessionId = getFirstSessionId(list, SessionSupports.SessionStateCode.END);

        if(sessionId < 1)
            throw new C2VException(ResponseCode.INTERNAL_SERVER_ERROR, "세션정보가 없습니다." );
        else {
            sessionId = 0L;
        }

        /* redis 에서 조회시간 가져오기 */
        var streaming = (Map<Object, Object>) redisPubService.get(RedisPubService.SESSION_KEY + sessionId);
        long streamingTime = streaming.get("streamingTime") == null ? 0 : (long)streaming.get("streamingTime");

        Map<String,Object> data = new HashMap<>();
        data.put("list", list);
        data.put("isStreaming"  , streaming.get("isStreaming") == null ? false : streaming.get("isStreaming"));
        data.put("streamingTime", streamingTime);

        long secDiff = (new Date().getTime() - streamingTime) / 1000;
        if(streamingTime > 0) {
            data.put("streamingTimeString", String.format("%d:%02d:%02d", secDiff / 3600, (secDiff % 3600) / 60, (secDiff % 60)));
        } else {
            data.put("streamingTimeString", "00:00:00");
        }

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }

    @Override
    public C2VResponse getEventProgramDetail(long eventId, long programId, long sessionId) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_cur_lang_cd"     , RequestUtils.getCurLangCd());
        params.put("in_session_id"      , sessionId);

        List<OpSessionDetailEntity> list = operatorRepository.getEventProgramDetail(params);

        if(list.size() < 1)
            throw new C2VException(ResponseCode.OK, "조회된 데이터가 없습니다.");

        OpSessionDetailEntity opSessionDetailEntity = list.get(0);
        OpSessionStaffsResponse opSessionStaffsResponse = OpSessionStaffsResponse.builder()
                .sessionId(opSessionDetailEntity.getSessionId())
                .sessionName(opSessionDetailEntity.getSessionName())
                .sessionDescription(opSessionDetailEntity.getSessionDescription())
                .startDatetime(opSessionDetailEntity.getStartDatetime())
                .endDatetime(opSessionDetailEntity.getEndDatetime())
                .sessionStaffs(list.stream()
                                .filter(s -> s.getSessionId().equals(opSessionDetailEntity.getSessionId()))
                                .map(p -> OpSessionStaffsResponse.SessionStaff.builder()
                                        .staffId(p.getStaffId())
                                        .staffType(p.getStaffType())
                                        .staffName(p.getStaffName())
                                        .staffDomainName(p.getStaffDomainName())
                                        .staffDescription(p.getStaffDescription())
                                        .staffPhotoPath(p.getStaffPhotoPath())
                                        .build()).toList())
                .build();

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(opSessionStaffsResponse).build();
    }

    @Override
    public C2VResponse getEventProgramSessionDisplays(long eventId, long programId, long sessionId) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_cur_lang_cd"     , RequestUtils.getCurLangCd());
        params.put("in_session_id"      , sessionId);
        List<OpSessionDisplayEntity> list = operatorRepository.getEventProgramSessionDisplays(params);

        if(list.size() < 1)
            throw new C2VException(ResponseCode.OK, "조회된 데이터가 없습니다.");

        var data = list.stream()
                    .map(s -> s.getScreenId()).distinct()
                    .map(s -> OpSessionDisplaysResponse.builder()
                                .screenId(s)
                                .sessionDisplays(
                                    list.stream()
                                        .filter(d -> d.getScreenId().equals(s))
                                        .map(d -> OpSessionDisplaysResponse.SessionDisplay.builder()
                                                    .displaySeq(d.getDisplaySeq())
                                                    .pageNo(d.getPageNo())
                                                    .displayCode(d.getDisplayCode())
                                                    .displayType(d.getDisplayType())
                                                    .displayContents(d.getDisplayContents())
                                                    .linkAddress(d.getLinkAddress())
                                                    .soundYn(d.getSoundYn())
                                                    .build()).toList()).build()).toList();

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }

    @Override
    public C2VResponse setEventProgramSessionChange(long eventId, long programId, long stopSessionId, long playSessionId, SessionSupports.SessionStateCode stateCode) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id", RequestUtils.getCurAdminId());
        params.put("in_program_id", programId);
        params.put("in_stop_session_id", stopSessionId);
        params.put("in_play_session_id", playSessionId);
        params.put("in_state_code", stateCode.getType());
        operatorRepository.setEventProgramSessionChange(params);

        /* 종료세션 redis 삭제 */


        /* publish */
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("command"          , OperatorSupports.CommandType.SessionChange.getValue());
        sendData.put("commandKey"       , this.getCommandKey());
        sendData.put("finishSessionId"  , stopSessionId);
        sendData.put("playSessionId"    , playSessionId);
        sendData.put("isSessionEnter"   , stateCode.equals(SessionSupports.SessionStateCode.ENTER) ? 1 : 0);
        redisPubService.publish(sendData);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).build();
    }

    @Override
    public C2VResponse setEventProgramSessionStateUpdate(long eventId, long programId, long sessionId, SessionSupports.SessionStateCode stateCode) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id"   , RequestUtils.getCurAdminId());
        params.put("in_session_id"     , sessionId);
        params.put("in_state_code"     , stateCode.getType());
        operatorRepository.setEventProgramSessionStateUpdate(params);

        Map<String, Object> sendData = new HashMap<>();
        sendData.put("command"          , OperatorSupports.CommandType.SessionStatusChange.getValue());
        sendData.put("commandKey"       , this.getCommandKey());
        sendData.put("sessionId"        , sessionId);
        sendData.put("isSessionEnter"   , stateCode.equals(SessionSupports.SessionStateCode.ENTER) ? 1 : 0);
        redisPubService.publish(sendData);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).build();
    }

    @Override
    public C2VResponse setEventProgramSessionStreamingChange(long eventId, long programId, long sessionId, String source, Integer sourceType, OperatorSupports.OnOffType isOnOffType) throws C2VException {

        String redisSessionKey  = RedisPubService.SESSION_KEY + sessionId;
        String key              = this.getCommandKey();
        Timestamp timestamp     = new Timestamp(System.currentTimeMillis());

        if(redisPubService.exists(redisSessionKey)) {
            Map<Object, Object> streamingData = redisPubService.get(redisSessionKey);
            timestamp = new Timestamp((long)streamingData.get("streamingTime"));
        }

        /* redis save */
        Map<String, Object>  params = new HashMap<>();
        params.put("isStreaming"    , isOnOffType.equals(OperatorSupports.OnOffType.On));
        params.put("streamingTime"  , timestamp);
        redisPubService.putAll(RedisPubService.SESSION_KEY + sessionId, params);

        /* redis send */
        params.clear();
        params.put("command", isOnOffType.equals(OperatorSupports.OnOffType.On) ? OperatorSupports.CommandType.StreamingStart.getValue() : OperatorSupports.CommandType.StreamingEnd.getValue());
        params.put("commandKey", key);
        params.put("sessionId", sessionId);
        params.put("source", source);
        params.put("sourceType",sourceType);
        params.put("streamingStartDatetime", timestamp);
        redisPubService.publish(params);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).build();
    }

    @Override
    public C2VResponse setEventProgramSessionMotionChange(long eventId, long programId, long sessionId, String motionType, String motionCode, OperatorSupports.OnOffType isOnOff) throws C2VException {

        /*
        * redis send
        * {"command":"MotionOnOff", "commandKey": "mice:op:cmd:1234", "sessionId": 2101, "motionType" : "a1234", "motionCode" : "b1234", "action" : "play" }
        * */
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("command"      , OperatorSupports.CommandType.MotionOnOff.getValue());
        sendData.put("commandKey"   , this.getCommandKey());
        sendData.put("sessionId"    , sessionId);
        sendData.put("motionType"   , motionType);
        sendData.put("motionCode"   , Long.parseLong(motionCode));
        sendData.put("action"       , isOnOff.equals(OperatorSupports.OnOffType.On) ? "play" : "stop");
        redisPubService.publish(sendData);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).build();
    }

    @Override
    public C2VResponse setEventProgramSessionMotionFavorite(String motionType, String motionCode) throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        params.put("in_motion_code"     , motionCode);
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(operatorRepository.setEventProgramSessionMotionFavorite(params)).build();
    }

    @Override
    public C2VResponse getEventProgramSessionMotionFavoriteList() throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        List<OpMotionEntity> data = operatorRepository.getEventProgramSessionMotionFavoriteList(params);
        List<CsvDto> motion = motionCodeDtoList.get("emotion").stream().skip(2).toList();
        List<CsvDto> localization = motionCodeDtoList.get("localizationStringForOffice").stream().skip(2).toList();

        var motionData = motion.stream().map(m -> {

            Map<String, Object> newMotion = new HashMap<>();
            newMotion.put("motion_type", m.getRow().get("EmotionType"));
            newMotion.put("motion_code", m.getRow().get("ID"));
            newMotion.put("isFav", data.stream().filter(f -> f.getMotionCode().toString().equals(m.getRow().get("ID").toString())).toList().size() > 0 ? true : false);
            Map<String,Object> motionName = new HashMap<>();
            try {
                var csvRow = localization.stream().filter(s -> s.getRow().get("ID").toString().equals(m.getRow().get("ChatCommand").toString())).findFirst();
                motionName.put("ko_kr", csvRow.get().getRow().get("KO"));
                motionName.put("en_us", csvRow.get().getRow().get("EN"));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            newMotion.put("motion_name", motionName);

            return newMotion;
        }).toList();

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(motionData).build();
    }

    @Override
    public C2VResponse setEventProgramSessionEffectChange(long eventId, long programId, long sessionId, String effectType, String effectCode, OperatorSupports.OnOffType isOnOffType) throws C2VException {

        /*
         * redis send
         * {"command":"MotionOnOff", "commandKey": "mice:op:cmd:1234", "sessionId": 2101, "motionType" : "a1234", "motionCode" : "b1234", "action" : "play" }
         * */
        Map<String, Object> sendData = new HashMap<>();
        sendData.put("command"      , OperatorSupports.CommandType.EffectOnOff.getValue());
        sendData.put("commandKey"   , this.getCommandKey());
        sendData.put("sessionId"    , sessionId);
        sendData.put("effectType"   , effectType);
        sendData.put("effectCode"   , Long.parseLong(effectCode));
        sendData.put("action"       , isOnOffType.equals(OperatorSupports.OnOffType.On) ? "play" : "stop");
        redisPubService.publish(sendData);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).build();
    }

    @Override
    public C2VResponse getEventProgramSessionEffectFavoriteList() throws C2VException {
        params.clear();
        params.put("in_cur_admin_id"    , RequestUtils.getCurAdminId());
        List<OpMotionEntity> data = operatorRepository.getEventProgramSessionMotionFavoriteList(params);
        List<CsvDto> motion = motionCodeDtoList.get("effect").stream().skip(2).toList();

        var motionData = motion.stream().map(m -> {

            Map<String, Object> newMotion = new HashMap<>();
            newMotion.put("motion_type", m.getRow().get("EffectMICEType"));
            newMotion.put("motion_code", m.getRow().get("ID"));
            newMotion.put("isFav", data.stream().filter(f -> f.getMotionCode().toString().equals(m.getRow().get("ID").toString())).toList().size() > 0 ? true : false);
            Map<String,Object> motionName = new HashMap<>();
            try {
                motionName.put("ko_kr", m.getRow().get("EffectName"));
                motionName.put("en_us", m.getRow().get("ResName"));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            newMotion.put("motion_name", motionName);

            return newMotion;
        }).toList();

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(motionData).build();
    }

    @Override
    public C2VResponse jobOperatorCommandRetry() throws  C2VException {
        Map<String, Object> data = new HashMap<>();
        List<String> keys = (List<String>)redisPubService.select();
        keys.forEach(k -> {
            var command = redisPubService.get(k);

            try {
                PubSubPayload.Status status = PubSubPayload.toStatus(command);

                if(Objects.isNull(status.getCommand())) return;

                if((status.getStatus().equals("fail") || status.getStatus().equals("send")) && status.getRetry() < 4) {
                    log.info("Job-Redis-Pub-retry {}", status);
                    redisPubService.payloadPublish(PubSubPayload.builder().commandKey(k).status(status).build());
                } else {
                    redisPubService.delete(k);
                }
            }
            catch (Exception ex) {
                log.error("jobOperatorCommandRetry {}", command);
                log.error("jobOperatorCommandRetry {}", ex);
            }
            finally {
                data.put(k, command);
            }
        });

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }

    @Override
    public C2VResponse setEventProgramStateChange(long eventId, long programId) throws C2VException {

        params.clear();
        params.put("in_cur_admin_id"   , RequestUtils.getCurAdminId());
        params.put("in_program_id"     , programId);
        operatorRepository.setEventProgramStateChange(params);

        Map<String, Object> sendData = new HashMap<>();
        sendData.put("command"          , OperatorSupports.CommandType.ProgramStatusChange.getValue());
        sendData.put("commandKey"       , this.getCommandKey());
        sendData.put("programId"        , programId);
        sendData.put("isAllCancel"      , 1);
        redisPubService.publish(sendData);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).build();
    }

    @Override
    public C2VResponse setEventStateChange(long eventId, EventSupports.EventStateCode stateCode, EventSupports.EventSpaceStateCode spaceStateCode) throws C2VException {

        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true); //대소문자 무시 여부
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //모르는 컬럼 에러처리여부

        Map<String, String> resultMap = new HashMap<>();

        if(spaceStateCode != null) {

            /* 커뮤니케이션 서버 연동 */
            params.clear();
            params.put("in_cur_admin_id", RequestUtils.getCurAdminId());
            params.put("in_event_id", eventId);
            List<OpSessionIdEntity> list = operatorRepository.getEventForSessionIdList(params);

            if (list.size() < 1)
                throw new C2VException(ResponseCode.OK, "조회된 데이터가 없습니다.");

            list.stream().forEach(s -> {

                /* 키 존재여부 체크 (redis 체크, group server 체크) */
                CommunicationPayload payload = this.getCommunicationPayload(this.getCommunicationKey(s.getSessionId().toString()));

                try {

                    if (spaceStateCode != null) {

                        /* communication 연동 */
                        if (spaceStateCode.equals(EventSupports.EventSpaceStateCode.OPEN)) {

                            if (payload.getArea() == null || Strings.isEmpty(payload.getArea().getGroupId())) {

                                try {
                                    //지역키 조회
                                    C2VResponse areaResult = groupFeignClient.getArea(commonUtils.getSystemToken(), s.getSessionId().toString());
                                    GroupServerResponse.Area area = objectMapper.convertValue(areaResult.getData(), GroupServerResponse.Area.class);
                                    payload.setArea(area);
                                } catch (Exception ex) {
                                    log.info(ex.getMessage());
                                }

                                if (payload.getArea() == null) {
                                    //지역키 생성
                                    GroupServerRequest.Area areaRequest = GroupServerRequest.Area.builder().groupName(s.getEventId().toString()).areaName(s.getSessionId().toString()).serviceType(3).build();
                                    C2VResponse areaResult = groupFeignClient.createArea(commonUtils.getSystemToken(), areaRequest);
                                    GroupServerResponse.Area area = objectMapper.convertValue(areaResult.getData(), GroupServerResponse.Area.class);
                                    payload.setArea(area);
                                }

                                //redis 등록
                                redisPubService.putAll(this.getCommunicationKey(s.getSessionId().toString()), objectMapper.convertValue(payload, Map.class));
                            }

                            if (payload.getStream() == null || Strings.isEmpty(payload.getStream().getStreamKey())) {

                                CommunicationPayload programPayload = this.getCommunicationPayload(this.getCommunicationKey(s.getProgramId().toString()));
                                if(programPayload.getStream() == null || Strings.isEmpty(programPayload.getStream().getStreamKey())) {
                                    String streamPath = String.format("Mice_Stream_%s_%s", s.getEventId().toString(), s.getProgramId().toString());

                                    //스트림키 생성
                                    GroupServerRequest.Stream streamRequest = GroupServerRequest.Stream.builder().streamPath(streamPath).build();
                                    C2VResponse streamResult = groupFeignClient.createStream(commonUtils.getSystemToken(), streamRequest);
                                    GroupServerResponse.Stream stream = objectMapper.convertValue(streamResult.getData(), GroupServerResponse.Stream.class);
                                    payload.setStream(stream);

                                    //redis 프로그램 단위
                                    redisPubService.putAll(this.getCommunicationKey(s.getProgramId().toString()), objectMapper.convertValue(payload, Map.class));
                                } else {
                                    payload.setStream(programPayload.getStream());
                                }
                                //redis 등록
                                redisPubService.putAll(this.getCommunicationKey(s.getSessionId().toString()), objectMapper.convertValue(payload, Map.class));
                            }

                            if (payload.getConference() == null || Strings.isEmpty(payload.getConference().getRoomId())) {

                                String serviceName = String.format("Mice_Room_%s", s.getSessionId().toString());

                                //미팅룸 생성
                                GroupServerRequest.Conference confRequest = GroupServerRequest.Conference.builder().serviceName(serviceName).build();

                                C2VResponse confResult = groupFeignClient.createConferenceMediaRoom(commonUtils.getSystemToken(), confRequest);
                                GroupServerResponse.Conference conference = objectMapper.convertValue(confResult.getData(), GroupServerResponse.Conference.class);
                                payload.setConference(conference);

                                //redis 등록
                                redisPubService.putAll(this.getCommunicationKey(s.getSessionId().toString()), objectMapper.convertValue(payload, Map.class));
                            }

                        } else if (spaceStateCode.equals(EventSupports.EventSpaceStateCode.CLOSED)) {

                            if (payload.getArea() != null && Strings.isNotEmpty(payload.getArea().getGroupId())) {
                                try {
                                    C2VResponse areaResult = groupFeignClient.deleteArea(commonUtils.getSystemToken(), payload.getArea().getGroupId());
                                } catch (FeignException ex) {
                                    log.error(ex.getMessage());
                                } finally {
                                    redisPubService.deleteForField(this.getCommunicationKey(s.getSessionId().toString()), "area");
                                }

//                                C2VResponse areaResult = groupFeignClient.deleteArea(commonUtils.getSystemToken(), payload.getArea().getGroupId());
//                                redisPubService.deleteForField(this.getCommunicationKey(s.getSessionId().toString()), "area");
                            }

                            if (payload.getStream() != null && Strings.isNotEmpty(payload.getStream().getStreamKey())) {
                                try {
                                    C2VResponse streamResult = groupFeignClient.deleteStream(commonUtils.getSystemToken(), payload.getStream().getStreamKey());
                                } catch (FeignException ex) {
                                    log.error(ex.getMessage());
                                } finally {
                                    redisPubService.deleteForField(this.getCommunicationKey(s.getSessionId().toString()), "stream");
                                }

//                                C2VResponse streamResult = groupFeignClient.deleteStream(commonUtils.getSystemToken(), payload.getStream().getStreamKey());
//                                redisPubService.deleteForField(this.getCommunicationKey(s.getSessionId().toString()), "stream");
                            }

                            if (payload.getConference() != null && Strings.isNotEmpty(payload.getConference().getRoomId())) {
                                try {
                                    C2VResponse conferenceResult = groupFeignClient.deleteConferenceMediaRoom(commonUtils.getSystemToken(), payload.getConference().getRoomId(), payload.getConference().getServiceName());
                                } catch (FeignException ex) {
                                    log.error(ex.getMessage());
                                } finally {
                                    redisPubService.deleteForField(this.getCommunicationKey(s.getSessionId().toString()), "conference");
                                }

//                                C2VResponse conferenceResult = groupFeignClient.deleteConferenceMediaRoom(commonUtils.getSystemToken(), payload.getConference().getRoomId(), payload.getConference().getServiceName());
//                                redisPubService.deleteForField(this.getCommunicationKey(s.getSessionId().toString()), "conference");
                            }

                            redisPubService.delete(this.getCommunicationKey(s.getSessionId().toString()));
                            redisPubService.delete(this.getCommunicationKey(s.getProgramId().toString()));
                        }
                    }

                } catch (Exception ex) {
                    resultMap.put(s.getSessionId().toString(), ex.getMessage());
                }
            });

        }

        if(resultMap.isEmpty()) {
            params.clear();
            params.put("in_cur_admin_id"        , RequestUtils.getCurAdminId());
            params.put("in_event_id"            , eventId);
            params.put("in_state_code"          , stateCode ==null ? 0 : stateCode.getType());
            params.put("in_space_state_code"    , spaceStateCode == null ? 0 : spaceStateCode.getType());
            operatorRepository.setEventStateChange(params);

            Map<String, Object> sendData = new HashMap<>();
            if(spaceStateCode != null) {
                sendData.clear();
                sendData.put("command", OperatorSupports.CommandType.EventSpaceStateChange.getValue());
                sendData.put("commandKey", this.getCommandKey());
                sendData.put("eventId", eventId);
                sendData.put("spaceStateCode", spaceStateCode.getType());
                redisPubService.publish(sendData);
            }

            if(stateCode != null) {
                sendData.clear();
                sendData.put("command", OperatorSupports.CommandType.EventDataChange.getValue());
                sendData.put("commandKey", this.getCommandKey());
                sendData.put("eventId", eventId);
                redisPubService.publish(sendData);
            }
        }
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(resultMap).build();
    }

    public String getCommandKey() {
        return RedisPubService.COMMAND_KEY.toString() + SequenceUtils.getSequence();
    }

    public String getCommunicationKey(String uid) {
        return RedisPubService.COMMUNICATION_KEY.toString() + uid;
    }

    private CommunicationPayload getCommunicationPayload(String CommunicationKey){

        CommunicationPayload payload = CommunicationPayload.builder().build();;

        if(redisPubService.exists(CommunicationKey)) {
            Map<Object, Object> redisData = redisPubService.get(CommunicationKey);
            if(redisData.get("area") != null )
                payload.setArea(objectMapper.convertValue(redisData.get("area"), GroupServerResponse.Area.class));
            if(redisData.get("stream") != null )
                payload.setStream(objectMapper.convertValue(redisData.get("stream"), GroupServerResponse.Stream.class));
            if(redisData.get("conference") != null )
                payload.setConference(objectMapper.convertValue(redisData.get("conference"), GroupServerResponse.Conference.class));
        }
        return payload;
    }
}
