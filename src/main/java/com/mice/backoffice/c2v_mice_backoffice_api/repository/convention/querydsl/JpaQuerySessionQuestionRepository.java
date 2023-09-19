package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionQuestionListOrder;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionQuestionStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.QSessionQuestionListDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.SessionQuestionListDto;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.SessionQuestionListQueryParam;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.QSessionQuestionEntity.sessionQuestionEntity;
import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.QSessionQuestionRecommendEntity.sessionQuestionRecommendEntity;

@Repository
@RequiredArgsConstructor
public class JpaQuerySessionQuestionRepository {
    private final JPAQueryFactory queryFactory;

    public Page<SessionQuestionListDto> findAllByCondition(SessionQuestionListQueryParam req, Pageable pageable, long sessionId) {
        List<SessionQuestionListDto> content = queryFactory
                .select(new QSessionQuestionListDto(
                        sessionQuestionEntity.questionSeq,
                        sessionQuestionEntity.account.surName,
                        sessionQuestionEntity.account.givenName,
                        sessionQuestionEntity.account.middleName,
                        sessionQuestionEntity.account.companyName.as("departmentName"),
                        sessionQuestionEntity.count().castToNum(Integer.class),
                        sessionQuestionEntity.viewCount,
                        sessionQuestionEntity.questionTitle.as("question"),
                        sessionQuestionEntity.questionDescription.as("description"),
                        sessionQuestionEntity.stateCode
                ))
                .from(sessionQuestionEntity)
                .leftJoin(sessionQuestionRecommendEntity)
                .on(sessionQuestionEntity.questionSeq.eq(sessionQuestionRecommendEntity.pk.questionSeq))
                .where(sessionQuestionEntity.sessionId.eq(sessionId)
                        .and(sessionQuestionEntity.stateCode.ne(SessionQuestionStateCode.ANSWER_COMPLETE))
                        .and(sessionQuestionEntity.useYn.eq('y')))
                .groupBy(sessionQuestionEntity.questionSeq)
                .orderBy(sort(req.getOrder(), req.isAsc()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(sessionQuestionEntity.count())
                .from(sessionQuestionEntity)
                .leftJoin(sessionQuestionRecommendEntity)
                .on(sessionQuestionEntity.questionSeq.eq(sessionQuestionRecommendEntity.pk.questionSeq))
                .where(sessionQuestionEntity.sessionId.eq(sessionId)
                        .and(sessionQuestionEntity.stateCode.ne(SessionQuestionStateCode.ANSWER_COMPLETE))
                        .and(sessionQuestionEntity.useYn.eq('y')));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);

    }

    private OrderSpecifier<?> [] sort(SessionQuestionListOrder sortOrder, boolean isAsc) {
        List<OrderSpecifier<?>> list = new ArrayList<>();

        Order order = isAsc ? Order.ASC : Order.DESC;

        switch (sortOrder) {
            case NAME -> {
                list.add(new OrderSpecifier<>(order, sessionQuestionEntity.account.surName));
                list.add(new OrderSpecifier<>(order, sessionQuestionEntity.account.givenName));
                list.add(new OrderSpecifier<>(order, sessionQuestionEntity.account.middleName));
            }
            case VIEW -> list.add(new OrderSpecifier<>(order, sessionQuestionEntity.viewCount));
            default -> list.add(new OrderSpecifier<>(order, sessionQuestionEntity.count()));
        }
        return list.toArray(new OrderSpecifier[0]);
    }
}
