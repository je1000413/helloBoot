package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaQueryEventHostRepository {
    private final JPAQueryFactory jpaQueryFactory;
}
