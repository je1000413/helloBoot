package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenDisplayStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenDisplayCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenDisplayGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenDisplayUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.screen.ScreenDisplayRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen.ScreenDisplayService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ScreenDisplayServiceImpl implements ScreenDisplayService {
    // Service
    private final LanguageService languageService;

    // Repository
    private final ScreenDisplayRepository screenDisplayRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void create(List<ScreenDisplayCreateRequest> list, ScreenEntity se) {
        for (ScreenDisplayCreateRequest x : list) {
            String seq = SequenceUtils.getSequence();

            ScreenDisplayEntity sde = ScreenDisplayEntity
                    .dataBuilder(x)
                    .displayContents(String.format("display_contents_%s", seq))
                    .fileName(String.format("file_name_%s", seq))
                    .linkAddress(String.format("link_address_%s", seq))
                    .screen(se)
                    .build();

            Map<String, Map<LanguageType, String>> messageMap = makeLanguageHashMap(x, seq);
            List<LanguageEntity> result = languageService.makeLanguageEntities(messageMap);

            languageService.saveAll(result);
            screenDisplayRepository.save(sde);
        }
    }

    @Override
    @Transactional
    public void update(List<ScreenDisplayUpdateRequest> list, ScreenEntity se) throws IOException {
        List<ScreenDisplayCreateRequest> createList = new ArrayList<>();

        List<Integer> displaySeqList = list.stream().map(ScreenDisplayUpdateRequest::getDisplaySeq).toList();
        List<ScreenDisplayEntity> screenDisplayNotDisplaySeqList = screenDisplayRepository.selectByScreenDisplayNotDisplaySeqs(displaySeqList, se.getScreenId(), ScreenDisplayStateCode.DELETE);

        screenDisplayNotDisplaySeqList.forEach(x -> {
            x.delete();

            // 다국어 삭제
            languageService.deleteByLanguageCode(x.getDisplayContents());
            languageService.deleteByLanguageCode(x.getFileName());
            languageService.deleteByLanguageCode(x.getLinkAddress());
        });

        for (ScreenDisplayUpdateRequest x : list) {
            if (x.getDisplaySeq() <= 0) {
                createList.add(ScreenDisplayCreateRequest.makeCreateRequest(x));
            } else {
                ScreenDisplayEntity screenDisplayEntity = screenDisplayRepository
                        .findById(x.getDisplaySeq())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("does not exist displaySeq: %s", x.getDisplaySeq())));

                screenDisplayEntity.update(x);

                languageService.updateLanguageEntityList(x.getDisplayContents(), screenDisplayEntity.getDisplayContents());
                languageService.updateLanguageEntityList(x.getFileName(), screenDisplayEntity.getFileName());
                languageService.updateLanguageEntityList(x.getLinkAddress(), screenDisplayEntity.getLinkAddress());
            }
        }

        if (!createList.isEmpty())
            create(createList, se);
    }

    @Override
    public List<ScreenDisplayGetResponse> list(List<ScreenDisplayEntity> list) {
        return list
                .stream()
                .filter(x -> x.getStateCode() != ScreenDisplayStateCode.DELETE)
                .map(x -> {

                    Map<LanguageType, String> displayContentsMap = languageService.makeLanguageMap(x.getDisplayContents());
                    Map<LanguageType, String> fileNameMap = languageService.makeLanguageMap(x.getFileName());
                    Map<LanguageType, String> linkAddressMap = languageService.makeLanguageMap(x.getLinkAddress());


                    return ScreenDisplayGetResponse
                            .dataBuilder(x)
                            .displayContents(displayContentsMap)
                            .linkAddress(linkAddressMap)
                            .fileName(fileNameMap)
                            .build();

                }).toList();
    }

    @Override
    public ScreenDisplayEntity save(ScreenDisplayEntity screenDisplayEntity) {
        System.out.println(screenDisplayEntity);

        screenDisplayRepository.save(screenDisplayEntity);

        return null;
    }

    @Override
    public void deleteLounge(ScreenEntity se, long screenId) {
        List<ScreenDisplayEntity> screenDisplayEntityList = screenDisplayRepository.findByScreenId(screenId);
        for (ScreenDisplayEntity x : screenDisplayEntityList) {
            ScreenDisplayEntity screenDisplayEntity = ScreenDisplayEntity.builder()
                    .screen(se)
                    .displaySeq(x.getDisplaySeq())
                    .pageNo(x.getPageNo())
                    .displayType(x.getDisplayType())
                    .displayContents(x.getDisplayContents())
                    .fileName(x.getFileName())
                    .soundYn(x.getSoundYn())
                    .linkAddress(x.getLinkAddress())
                    .stateCode(ScreenDisplayStateCode.DELETE)
                    .createUserId(se.getCreateUserId())
                    .createDatetime(se.getCreateDatetime())
                    .updateUserId(RequestUtils.getCurAdminId())
                    .updateDatetime(LocalDateTime.now())
                    .build();

            screenDisplayRepository.save(screenDisplayEntity);

            languageService.deleteByLanguageCode(x.getDisplayContents());
            languageService.deleteByLanguageCode(x.getFileName());
            languageService.deleteByLanguageCode(x.getLinkAddress());
        }

    }

    private Map<String, Map<LanguageType, String>> makeLanguageHashMap(ScreenDisplayCreateRequest req, String seq) {
        Map<String, Map<LanguageType, String>> languageMap = new HashMap<>();
        Map<LanguageType, String> linkAddress = Objects.requireNonNullElseGet(req.getLinkAddress(), HashMap::new);
        Map<LanguageType, String> displayContents = Objects.requireNonNullElseGet(req.getDisplayContents(), HashMap::new);
        Map<LanguageType, String> fileName = Objects.requireNonNullElseGet(req.getFileName(), HashMap::new);

        if (displayContents.isEmpty())
            displayContents.put(LanguageType.KO_KR, "");

        if (linkAddress.isEmpty())
            linkAddress.put(LanguageType.KO_KR, "");

        if (fileName.isEmpty())
            fileName.put(LanguageType.KO_KR, "");

        languageMap.put(String.format("display_contents_%s", seq), displayContents);
        languageMap.put(String.format("link_address_%s", seq), linkAddress);
        languageMap.put(String.format("file_name_%s", seq), fileName);

        return languageMap;
    }
}