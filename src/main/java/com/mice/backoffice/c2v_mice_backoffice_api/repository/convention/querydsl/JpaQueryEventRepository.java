package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.EventLanguageDto;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.QLanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventCreateRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.QLanguageEntity.*;
import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.QEventEntity.*;

@Repository
@RequiredArgsConstructor
public class JpaQueryEventRepository {
    private final JPAQueryFactory queryFactory;
}
