package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionForParentEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session.SessionCustomRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session.SessionRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen.ScreenService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.PackageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionSpaceInfoService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionStaffService;
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
public class SessionServiceImpl implements SessionService {
    // service
    private final LanguageService languageService;
    private final SessionStaffService sessionStaffService;
    private final SessionSpaceInfoService sessionSpaceInfoService;
    private final PackageService packageService;
    private final ScreenService screenService;

    //repository
    private final SessionRepository sessionRepository;
    private final SessionCustomRepository sessionCustomRepository;

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void create(SessionCreateRequest sr, ProgramEntity pe) {
        String seq = SequenceUtils.getSequence();

/*      Map<String, String> messageMap = makeLanguageHashMap(seq, sr);
        List<LanguageEntity> result = languageService.makeLanguageEntities(messageMap);

        languageService.saveAll(result);*/

        SessionEntity se = SessionEntity.dataBuilder(sr)
                .program(pe)
                .sessionName("session_name_" + seq)
                .sessionDescription("session_description_" + seq)
                .build();

        sessionRepository.save(se);
    }

    @Override
    @Transactional
    public void create(List<SessionCreateRequest> list, ProgramEntity pe) {
        list.forEach(x -> {

            String seq = SequenceUtils.getSequence();

            SessionEntity se = SessionEntity
                    .dataBuilder(x)
                    .program(pe)
                    .sessionName(String.format("session_name_%s", seq))
                    .sessionDescription(String.format("session_description_%s", seq))
                    .hallName(String.format("hall_name_%s", seq))
                    .build();

            Map<String, Map<LanguageType, String>> messageMap = makeLanguageHashMap(seq, x);
            List<LanguageEntity> result = languageService.makeLanguageEntities(messageMap);

            languageService.saveAll(result);
            sessionRepository.save(se);

            // 세션 staff(사회자, 연사) 저장
            sessionStaffService.create(x.getSessionStaffs(), se);

            // 세션 커버 이미지 저장
            screenService.create(x.getSessionCoverImages(), se.getSessionId(), ScreenMappingType.SESSION);

            // 모바일 하단 배너 저장
            screenService.create(x.getSessionMobileBottomBanners(), se.getSessionId(), ScreenMappingType.SESSION);

            // 온라인 공간 정보 저장
            x.getOnlineSpaceInfo().forEach(e -> screenService.create(e.getSessionDisplays(), se.getSessionId(), ScreenMappingType.SESSION));

            // 상품 정보 저장
            packageService.create(x.getPackages(), se, pe.getEvent().getEventId());

            // 첨부 파일 저장
            screenService.create(x.getAttachments(), se.getSessionId(), ScreenMappingType.SESSION);
        });
    }

    @Override
    public List<SessionEntity> findAllBySessionIds(List<Long> sessionIds) {
        return sessionRepository.findAllById(sessionIds);
    }

    @Override
    @Transactional
    public void update(List<SessionUpdateRequest> list, ProgramEntity pe) {

        // 기존에 존재하는 항목만 가져오고 리스트에 존재하지 않는 대상에 대해서는 삭제
        List<Long> sessionIdList = list
                .stream()
                .map(SessionUpdateRequest::getSessionId)
                .filter(sessionId -> sessionId > 0)
                .toList();

        // 삭제 대상
        List<SessionEntity> deleteSessions = sessionRepository.findAllBySessionIdNotInAndProgramProgramId(sessionIdList, pe.getProgramId());
        deleteSessions.forEach(SessionEntity::delete);

        // sessionId가 없는 대상은 신규 추가 대상이라고 판별
        List<SessionCreateRequest> sessionCreateList = list
                .stream()
                .filter(x -> x.getSessionId() == 0)
                .map(SessionCreateRequest::makeCreateRequest)
                .toList();

        list.stream().filter(x -> x.getSessionId() > 0).forEach(x -> {

            SessionEntity se = sessionRepository
                    .findById(x.getSessionId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Session Entity does not exist SessionId: %s", x.getSessionId())));

            // 대상이 삭제 되지 않는 대상일 때만 업데이트 한다.
            if (Character.toLowerCase(se.getUseYn()) == 'y') {
                // 세션 다국어 업데이트
                languageService.updateLanguageEntityList(x.getSessionName(), se.getSessionName());
                languageService.updateLanguageEntityList(x.getSessionDescription(), se.getSessionDescription());
                languageService.updateLanguageEntityList(x.getHallName(), se.getHallName());

                // 세션 업데이트
                se.update(x);

                // 세션 스테프 업데이트
                sessionStaffService.update(x.getSessionStaffs(), se);

                // 상품 정보 update
                packageService.update(x.getPackages(), se, pe.getEvent().getEventId());

                // 세션 커버 이미지 업데이트
                screenService.update(x.getSessionCoverImages(), se.getSessionId(), ScreenMappingType.SESSION);

                // 모바일 하단 배너 업데이트
                screenService.update(x.getSessionMobileBottomBanners(), se.getSessionId(), ScreenMappingType.SESSION);

                // 온라인 공간 정보 업데이트
                x.getOnlineSpaceInfos().forEach(e -> screenService.update(e.getSessionDisplays(), se.getSessionId(), ScreenMappingType.SESSION));

                // 첨부 파일 업데이트
                screenService.update(x.getAttachments(), se.getSessionId(), ScreenMappingType.SESSION);
            }
        });

        // 신규 대상은 가장 마지막에 생성
        if (!sessionCreateList.isEmpty())
            create(sessionCreateList, pe);
    }

    @Override
    public List<SessionGetResponse> list(List<SessionEntity> list) throws C2VException {
        return list
                .stream()
                .filter(x -> Character.toLowerCase(x.getUseYn()) == 'y')
                .map(x -> {
                    Map<LanguageType, String> sessionNameMap = languageService.makeLanguageMap(x.getSessionName());
                    Map<LanguageType, String> sessionDescriptionMap = languageService.makeLanguageMap(x.getSessionDescription());
                    Map<LanguageType, String> hallNameMap = languageService.makeLanguageMap(x.getHallName());

                    // 세션 직원 list
                    List<SessionStaffGetResponse> sessionStaffList = sessionStaffService.list(x.getSessionStaffs());

                    List<ScreenGetResponse> screenList = screenService.list(x.getScreens());
                    Map<DisplayCode, List<ScreenGetResponse>> displayCodeListMap = screenList
                            .stream()
                            .collect(Collectors
                                    .groupingBy(ScreenGetResponse::getDisplayCode));

                    // 첨부파일 list
                    List<ScreenGetResponse> attachmentList = displayCodeListMap.getOrDefault(DisplayCode.ATTACHMENT, new ArrayList<>());
                    // 커버 이미지 list
                    List<ScreenGetResponse> coverImageList = displayCodeListMap.getOrDefault(DisplayCode.COVER_IMAGE, new ArrayList<>());
                    // 모바일 하단 배너 list
                    List<ScreenGetResponse> sessionMobileBottomBannerList = displayCodeListMap.getOrDefault(DisplayCode.MOBILE_BOTTOM_BANNER, new ArrayList<>());

                    displayCodeListMap.remove(DisplayCode.ATTACHMENT);
                    displayCodeListMap.remove(DisplayCode.COVER_IMAGE);
                    displayCodeListMap.remove(DisplayCode.MOBILE_BOTTOM_BANNER);

                    // 온라인 공간 정보
                    List<SessionSpaceInfoGetResponse> sessionSpaceInfoList = sessionSpaceInfoService.list(displayCodeListMap);

                    // 패키지 정보
                    List<PackageGetResponse> packageList = packageService.list(x.getSessionId());

                    // 선행 세션 정보
                    SessionEntity parentSession = sessionRepository
                            .findById(x.getParentSessionId())
                            .orElse(null);

                    Map<LanguageType, String>  parentSessionNameMap = Objects.isNull(parentSession) ? new EnumMap<>(LanguageType.class) : languageService.makeLanguageMap(parentSession.getSessionName());

                    return SessionGetResponse
                            .dataBuilder(x)
                            .sessionName(sessionNameMap)
                            .sessionDescription(sessionDescriptionMap)
                            .parentSessionName(parentSessionNameMap)
                            .hallName(hallNameMap)
                            .sessionStaffs(sessionStaffList)
                            .attachments(attachmentList)
                            .sessionCoverImages(coverImageList)
                            .sessionMobileBottomBanners(sessionMobileBottomBannerList)
                            .onlineSpaceInfos(sessionSpaceInfoList)
                            .packages(packageList)
                            .build();
                }).toList();
    }

    private Map<String, Map<LanguageType,String>> makeLanguageHashMap(String seq, SessionCreateRequest sr) {
        Map<String, Map<LanguageType,String>> languageMap = new HashMap<>();

        languageMap.put(String.format("session_name_%s", seq), sr.getSessionName());
        languageMap.put(String.format("session_description_%s", seq), sr.getSessionDescription());
        languageMap.put(String.format("hall_name_%s", seq), sr.getHallName());

        return languageMap;
    }


    public C2VResponse findSessionForParent(SessionListForParentRequest req) throws C2VException {
        Map<String, Object> params                  = new LinkedHashMap<>();
        params.put("in_cur_admin_id"                , RequestUtils.getCurAdminId());
        params.put("in_event_id"                    , req.getEventId());
        params.put("in_search_program_type_code"    , req.getSearchProgramTypeCode());
        params.put("in_search_session_type"         , req.getSearchSessionType());
        params.put("in_search_session_value"        , req.getSearchSessionValue());
        params.put("in_page_size"                   , req.getPageSize());
        params.put("in_page_num"                    , req.getPageNum());

        List<SessionForParentEntity> list = sessionCustomRepository.findSessionForParent(params);

        var data = new HashMap<String, Object>();
        data.put("totalCount", list.size() > 0 ? list.get(0).getTotalCount() : 0);
        data.put("list", list);

        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();
    }

}
