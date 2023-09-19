package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.MakeLanguageDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity.LanguageId;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.LanguageRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
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
public class LanguageServiceImpl implements LanguageService {
    // Repository
    private final LanguageRepository languageRepository;

    @Override
    public void save(LanguageEntity le) {
        languageRepository.save(le);
    }

    @Override
    public void saveAll(List<LanguageEntity> list) {
        languageRepository.saveAll(list);
    }

    @Override
    @Transactional
    public void updateLanguageEntityList(Map<LanguageType, String> messageMap, String languageCode) {
        List<LanguageEntity> list = findAllByPkLanguageCode(languageCode);

        // 전체 삭제인 경우
        if (Objects.isNull(messageMap)) {
            list.forEach(LanguageEntity::delete);
        } else {
            Set<LanguageType> keySet = new HashSet<>(messageMap.keySet());

            // 기존에 존재 하는 항목에 대해서 업데이트
            list.forEach(x -> {
                LanguageType languageType = x.getLanguageId().getLanguageType();
                if (messageMap.containsKey(languageType)) {
                    String message = messageMap.get(languageType);

                    x.updateLanguageEntity(message);
                    keySet.remove(languageType);
                }
            });

            // 기존에 없던 다국어에 대해서는 새롭게 추가
            if (!keySet.isEmpty()) {
                keySet.forEach(x -> {
                    LanguageId languageId = LanguageId
                            .builder()
                            .languageCode(languageCode)
                            .languageType(x)
                            .build();

                    LanguageEntity languageEntity = LanguageEntity
                            .builder()
                            .languageId(languageId)
                            .useYn('y')
                            .createUserId(RequestUtils.getCurAdminId())
                            .updateUserId(RequestUtils.getCurAdminId())
                            .message(messageMap.get(x))
                            .build();

                    languageRepository.save(languageEntity);
                });
            }

            // 기존에 존재 하였으나, 사용자가 삭제한 대상에 대해서는 useYn 플래그 변경
            List<LanguageEntity> deleteLanguageList = languageRepository.findAllByNotInLanguageType(languageCode, messageMap.keySet());
            deleteLanguageList.forEach(LanguageEntity::delete);
        }
    }

    @Override
    public List<LanguageEntity> makeLanguageEntities(Map<String, Map<LanguageType,String>> messageMap) {
        List<LanguageEntity> result = new ArrayList<>();

        messageMap.forEach((key, innerMap) -> innerMap.forEach((type, value) -> {
            LanguageId languageId = LanguageId
                    .builder()
                    .languageCode(key)
                    .languageType(type)
                    .build();

            LanguageEntity languageEntity = LanguageEntity
                    .builder()
                    .languageId(languageId)
                    .useYn('y')
                    .message(value)
                    .createUserId(RequestUtils.getCurAdminId())
                    .updateUserId(RequestUtils.getCurAdminId())
                    .build();

            result.add(languageEntity);
        }));

        return result;
    }

    @Override
    public List<LanguageEntity> makeLanguageEntities(MakeLanguageDto languageDto) {
        List<LanguageEntity> result = new ArrayList<>();

        languageDto.getMessageMap().forEach((key, innerMap) -> innerMap.forEach((type, value) -> {
            LanguageId languageId = LanguageId
                    .builder()
                    .languageCode(key)
                    .languageType(type)
                    .build();

            LanguageEntity languageEntity = LanguageEntity
                    .builder()
                    .languageId(languageId)
                    .useYn(languageDto.getUseYn())
                    .message(value)
                    .createUserId(languageDto.getCreateUserId())
                    .updateUserId(languageDto.getUpdateUserId())
                    .build();

            result.add(languageEntity);
        }));

        return result;
    }

    @Override
    @Transactional
    public List<LanguageEntity> findAllByPkLanguageCode(String languageCode) {
        return languageRepository.findAllByPkLanguageCode('y', languageCode);
    }
    @Override
    @Transactional
    public LanguageEntity findByLanguageId(LanguageId languageId) {
        String errorMsg = String.format("language entity does not exist languageCode: %s languageType: %s", languageId.getLanguageCode(), languageId.getLanguageType());

        return languageRepository
                .findById(languageId)
                .orElseThrow(() -> new EntityNotFoundException(errorMsg));
    }

    @Override
    @Transactional
    public Map<LanguageType, String> makeLanguageMap(String languageCode) {
        List<LanguageEntity> list = findAllByPkLanguageCode(languageCode);
        return list.stream().collect(Collectors.toMap(LanguageEntity::getLanguageType, LanguageEntity::getMessage));
    }

    @Override
    @Transactional
    public void deleteByLanguageCode(String languageCode) {
        List<LanguageEntity> languageList = findAllByPkLanguageCode(languageCode);
        languageList.forEach(LanguageEntity::delete);
    }
}
