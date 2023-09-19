package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.AudienceSortType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.AudienceDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.AudienceListParam;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.QAudienceDto;
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

import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.account.QAccountEntity.accountEntity;


@Repository
@RequiredArgsConstructor
public class JpaQueryAccountRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Page<AudienceDto> findAudienceWithPaging(AudienceListParam param, Pageable pageable) {

        JPAQuery<Long> count = jpaQueryFactory
                .select(accountEntity.count())
                .from(accountEntity)
                .where(accountEntity
                        .accountId
                        .in(param.getAudienceIds()));

        List<AudienceDto> audiences = jpaQueryFactory
                .select(new QAudienceDto(
                        accountEntity.accountId,
                        accountEntity.surName.concat(accountEntity.givenName).as("userName"),
                        accountEntity.companyName.as("departmentName"),
                        accountEntity.photoPath
                ))
                .from(accountEntity)
                .where(accountEntity
                        .accountId
                        .in(param.getAudienceIds()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return PageableExecutionUtils.getPage(audiences, pageable, count::fetchOne);
    }

    private OrderSpecifier<?> [] sort(AudienceSortType sortType, boolean isAsc) {
        List<OrderSpecifier<?>> list = new ArrayList<>();
        Order order = (isAsc) ? Order.ASC : Order.DESC;

        if (sortType == AudienceSortType.NAME) {
            list.add(new OrderSpecifier<>(order, accountEntity.surName));
            list.add(new OrderSpecifier<>(order, accountEntity.middleName));
        }

        return list.toArray(new OrderSpecifier[0]);
    }
}
