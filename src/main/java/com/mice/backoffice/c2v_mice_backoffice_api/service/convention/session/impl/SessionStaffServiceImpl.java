package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.MakeLanguageDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionStaffEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session.SessionStaffRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionStaffService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class SessionStaffServiceImpl implements SessionStaffService {
    // Service
    private final LanguageService languageService;

    // Repository
    private final SessionStaffRepository sessionStaffRepository;

    @Override
    @Transactional
    public void create(List<SessionStaffCreateRequest> list, SessionEntity se) {
        if (!Objects.isNull(list)) {
            list.forEach(x -> {
                String seq = SequenceUtils.getSequence();

                SessionStaffEntity sse = SessionStaffEntity
                        .dataBuilder(x)
                        .session(se)
                        .staffName(String.format("staff_name_%s", seq))
                        .domainName(String.format("domain_name_%s", seq))
                        .staffDescription(String.format("staff_description_%s", seq))
                        .photoPath(String.format("photo_path_%s", seq))
                        .build();

                Map<String, Map<LanguageType, String>> messageMap = makeLanguageHashMap(seq, x);
                List<LanguageEntity> result = languageService.makeLanguageEntities(messageMap);

                languageService.saveAll(result);
                sessionStaffRepository.save(sse);
            });
        }
    }

    @Override
    public List<SessionStaffGetResponse> list(List<SessionStaffEntity> list) {
        return list
                .stream()
                .filter(x -> Character.toLowerCase(x.getUseYn()) == 'y')
                .map(x -> {
                    Map<LanguageType, String> domainNameMap = languageService.makeLanguageMap(x.getDomainName());
                    Map<LanguageType, String> staffNameMap = languageService.makeLanguageMap(x.getStaffName());
                    Map<LanguageType, String> staffDescriptionMap = languageService.makeLanguageMap(x.getStaffDescription());
                    Map<LanguageType, String> photoPathMap = languageService.makeLanguageMap(x.getPhotoPath());

                    return SessionStaffGetResponse
                            .dataBuilder(x)
                            .domainName(domainNameMap)
                            .staffName(staffNameMap)
                            .staffDescription(staffDescriptionMap)
                            .photoPath(photoPathMap)
                            .build();
                }).toList();
    }

    @Override
    @Transactional
    public void update(List<SessionStaffUpdateRequest> list, SessionEntity se) {
        if (!Objects.isNull(list)) {
            List<Long> sessionStaffIdList = list
                    .stream()
                    .map(SessionStaffUpdateRequest::getStaffId)
                    .distinct()
                    .toList();

            List<SessionStaffEntity> deleteSessionStaffList = sessionStaffRepository.findAllByStaffIdNotInAndSessionSessionIdAndUseYn(sessionStaffIdList, se.getSessionId(), 'y');

            deleteSessionStaffList.forEach(SessionStaffEntity::delete);

            List<SessionStaffCreateRequest> sessionStaffCreateList = list
                    .stream()
                    .filter(x -> x.getStaffId() == 0)
                    .map(SessionStaffCreateRequest::makeCreateRequest)
                    .toList();

            if (!list.isEmpty())
                create(sessionStaffCreateList, se);

            list.stream().filter(x -> x.getStaffId() > 0).forEach(x -> {
                SessionStaffEntity sse = sessionStaffRepository
                        .findById(x.getStaffId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("SessionStaff entity does not exist staffId: %s", x.getStaffId())));

                if (Character.toLowerCase(sse.getUseYn()) == 'y') {
                    // 다국어 업데이트
                    languageService.updateLanguageEntityList(x.getStaffName(), sse.getStaffName());
                    languageService.updateLanguageEntityList(x.getStaffDescription(), sse.getStaffDescription());
                    languageService.updateLanguageEntityList(x.getDomainName(), sse.getDomainName());
                    languageService.updateLanguageEntityList(x.getPhotoPath(), sse.getPhotoPath());

                    sse.update(x);
                }
            });
        } else {
            List<SessionStaffEntity> deleteSessionStaffList = sessionStaffRepository.findAllByStaffIdNotInAndSessionSessionIdAndUseYn(new ArrayList<>(), se.getSessionId(), 'y');
            deleteSessionStaffList.forEach(SessionStaffEntity::delete);
        }
    }

    private Map<String, Map<LanguageType,String>> makeLanguageHashMap(String seq, SessionStaffCreateRequest sr) {
        Map<String, Map<LanguageType,String>> languageMap = new HashMap<>();

        languageMap.put(String.format("staff_name_%s", seq), sr.getStaffName());
        languageMap.put(String.format("domain_name_%s", seq), sr.getDomainName());
        languageMap.put(String.format("staff_description_%s", seq), sr.getStaffDescription());
        languageMap.put(String.format("photo_path_%s", seq), sr.getPhotoPath());

        return languageMap;
    }

    private Map<LanguageType, String> makePhotoPath() {
        Map<LanguageType, String> photoPath = new EnumMap<>(LanguageType.class);

        photoPath.putIfAbsent(LanguageType.KO_KR, "");

        return photoPath;
    }
}
