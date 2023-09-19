package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity.LanguageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface LanguageRepository extends JpaRepository<LanguageEntity, LanguageId>  {
    @Query("select l " +
            "from LanguageEntity l " +
            "where l.languageId.languageCode =:language_code " +
            "and l.useYn =:use_yn")
    List<LanguageEntity> findAllByPkLanguageCode(@Param("use_yn") char useYn,
                                                 @Param("language_code") String languageCode);

    @Query("select l " +
            "from LanguageEntity l " +
            "where l.languageId.languageCode = :language_code " +
            "and l.languageId.languageType not in :language_types " +
            "and l.useYn = 'y'")
    List<LanguageEntity> findAllByNotInLanguageType(@Param("language_code") String languageCode,
                                                    @Param("language_types") Collection<LanguageType> languageTypes);
}
