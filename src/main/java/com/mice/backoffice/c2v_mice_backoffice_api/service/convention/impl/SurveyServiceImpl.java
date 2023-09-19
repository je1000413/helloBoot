package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.SurveyStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.MakeLanguageDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.SurveyEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.SurveyUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.LanguageRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.SurveyRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.SurveyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    // Service
    private final LanguageService languageService;

    // Repository
    private final SurveyRepository surveyRepository;

    @Override
    public void create(List<SurveyCreateRequest> list, long surveyMappingId, Supports.SurveyMappingType surveyMappingType) {
        if (Objects.nonNull(list)) {
            list.forEach(x -> {
                String seq = SequenceUtils.getSequence();

                SurveyEntity entity = SurveyEntity
                        .dataBuilder(x, surveyMappingId, surveyMappingType)
                        .surveyPath(String.format("survey_path_%s", seq))
                        .build();

                Map<String, Map<LanguageType, String>> messageMap = makeLanguageHashMap(x, seq);
                List<LanguageEntity> result = languageService.makeLanguageEntities(messageMap);

                languageService.saveAll(result);
                surveyRepository.save(entity);
            });
        }
    }

    @Override
    @Transactional
    public void update(List<SurveyUpdateRequest> list, long surveyMappingId, Supports.SurveyMappingType surveyMappingType) {
        List<SurveyCreateRequest> createList = new ArrayList<>();
        List<Long> surveyNos = list
                .stream()
                .map(SurveyUpdateRequest::getSurveyNo)
                .distinct()
                .toList();

        List<SurveyEntity> surveyEntityList = surveyRepository.findBySurveyNotSurveyNos(surveyNos, surveyMappingId, SurveyStateCode.DELETE);

        surveyEntityList.forEach(x -> {
            x.delete();
            languageService.deleteByLanguageCode(x.getSurveyPath());
        });

        list.forEach(x -> {
            if (Objects.isNull(x.getSurveyNo())) {
                createList.add(SurveyCreateRequest.makeCreateRequest(x));
            } else {
                SurveyEntity surveyEntity = surveyRepository
                        .findById(x.getSurveyNo())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("entity does not exist surveyNo: %s", x.getSurveyNo())));

                surveyEntity.update(x);
                languageService.updateLanguageEntityList(x.getSurveyPath(), surveyEntity.getSurveyPath());
            }
        });

        if (!createList.isEmpty())
            create(createList, surveyMappingId, Supports.SurveyMappingType.EVENT);
    }

    @Override
    public List<SurveyGetResponse> listBySurveyMappingId(long surveyMappingId) {
        List<SurveyEntity> list = surveyRepository.findAllBySurveyMappingIdAndStateCodeNot(surveyMappingId, SurveyStateCode.DELETE);

        return list.stream().map(x -> {
            Map<LanguageType, String> surveyPathMap = languageService.makeLanguageMap(x.getSurveyPath());

            return SurveyGetResponse
                    .dataBuilder(x)
                    .surveyPath(surveyPathMap)
                    .build();
        }).toList();
    }

    private Map<String, Map<LanguageType,String>> makeLanguageHashMap(SurveyCreateRequest req, String seq) {
        Map<String, Map<LanguageType, String>> languageMap = new HashMap<>();

        languageMap.put(String.format("survey_path_%s", seq), req.getSurveyPath());

        return languageMap;
    }
}
