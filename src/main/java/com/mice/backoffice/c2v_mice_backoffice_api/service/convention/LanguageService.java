package com.mice.backoffice.c2v_mice_backoffice_api.service.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.MakeLanguageDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;

import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity.*;

public interface LanguageService {
     void save(LanguageEntity le);
     void saveAll(List<LanguageEntity> list);
     void updateLanguageEntityList(Map<LanguageType,String> messageMap, String languageCode);
     List<LanguageEntity> makeLanguageEntities(Map<String, Map<LanguageType,String>> messageMap);
     List<LanguageEntity> makeLanguageEntities(MakeLanguageDto languageDto);

     List<LanguageEntity> findAllByPkLanguageCode(String languageCode);

     LanguageEntity findByLanguageId(LanguageId languageId);

     Map<LanguageType, String> makeLanguageMap(String languageCode);
     void deleteByLanguageCode(String languageCode);
}
