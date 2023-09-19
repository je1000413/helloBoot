package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.MakeLanguageDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionDisplayEntity.SessionDisplayId;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionDisplayUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session.SessionDisplayRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionDisplayService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SessionDisplayServiceImpl implements SessionDisplayService {
    // Service
    private final LanguageService languageService;

    // Repository
    private final SessionDisplayRepository sessionDisplayRepository;

    @Override
    @Transactional
    public void create(List<SessionDisplayCreateRequest> list, SessionEntity se) {
        list.forEach(x -> {
            String seq = SequenceUtils.getSequence();
            int sessionDisplaySeq = sessionDisplayRepository.findSessionDisplayId();

            SessionDisplayId sessionDisplayId = SessionDisplayId
                    .builder()
                    .displaySeq(sessionDisplaySeq)
                    .session(se)
                    .build();

            SessionDisplayEntity sde = SessionDisplayEntity
                    .dataBuilder(x)
                    .sessionDisplayId(sessionDisplayId)
                    .displayContents(String.format("display_contents_%s", seq))
                    .build();

            Map<String, Map<LanguageType, String>> messageMap = makeLanguageHashMap(seq, x);
            List<LanguageEntity> result = languageService.makeLanguageEntities(messageMap);

            languageService.saveAll(result);
            sessionDisplayRepository.save(sde);
        });
    }

    @Override
    public List<SessionDisplayGetResponse> list(List<SessionDisplayEntity> list) {
        return list
                .stream()
                .filter(x -> x.getStateCode() != SessionDisplayStateCode.DELETE)
                .map(x -> {
                    Map<LanguageType, String> displayContentsMap = languageService.makeLanguageMap(x.getDisplayContents());

                    return SessionDisplayGetResponse
                            .dataBuilder(x)
                            .displayContents(displayContentsMap)
                            .build();
                }).toList();
    }

    @Override
    @Transactional
    public void update(List<SessionDisplayUpdateRequest> list, SessionEntity se, DisplayCode displayCode) {
        List<Long> sessionDisplayIdList = list
                .stream()
                .map(SessionDisplayUpdateRequest::getDisplaySeq)
                .distinct()
                .toList();

        // 기존에 존재 하였으나, 없어진 항목 찾아서 삭제
        List<SessionDisplayEntity> deleteSessionDisplayList = sessionDisplayRepository.findAllByDisplaySeqNotInAndDisplayCode(sessionDisplayIdList, displayCode, se.getSessionId(), SessionDisplayStateCode.DELETE);
        deleteSessionDisplayList.forEach(SessionDisplayEntity::delete);

        // 새롭게 추가 된 항목들
        List<SessionDisplayCreateRequest> sessionCreateList = list
                .stream()
                .filter(x -> x.getDisplaySeq() == 0)
                .map(SessionDisplayCreateRequest::makeCreateRequest)
                .toList();

        // 기존 항목은 업데이트
        list
                .stream()
                .filter(x -> x.getDisplaySeq() > 0)
                .forEach(x -> {
                    SessionDisplayId sessionDisplayId = SessionDisplayId
                            .builder()
                            .session(se)
                            .displaySeq(x.getDisplaySeq())
                            .build();

                    SessionDisplayEntity sde = sessionDisplayRepository
                            .findById(sessionDisplayId)
                            .orElseThrow(() -> new EntityNotFoundException(String.format("SessionDisplay Entity does not exist displaySeq: %s", x.getDisplaySeq())));

                    if (sde.getStateCode() != SessionDisplayStateCode.DELETE) {
                        languageService.updateLanguageEntityList(x.getDisplayContents(), sde.getDisplayContents());
                        sde.update(x);
                    }
                });

        // 마지막에 신규 항목 추가
        if (!sessionCreateList.isEmpty())
            create(sessionCreateList, se);
    }

    private Map<String, Map<LanguageType,String>> makeLanguageHashMap(String seq, SessionDisplayCreateRequest sr) {
        Map<String, Map<LanguageType,String>> languageMap = new HashMap<>();
        Map<LanguageType, String> displayContents = sr.getDisplayContents();

        if (displayContents.isEmpty())
            displayContents.put(LanguageType.KO_KR, "");

        languageMap.put(String.format("display_contents_%s", seq), displayContents);

        return languageMap;
    }
}
