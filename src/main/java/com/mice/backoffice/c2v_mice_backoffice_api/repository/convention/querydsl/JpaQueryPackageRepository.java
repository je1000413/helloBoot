package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.*;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.event.EventPackageListDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.event.QEventPackageListDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.QLanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage.EventPackageQueryParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.QDomainEntity.domainEntity;
import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.QLanguageEntity.languageEntity;
import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.QEventEntity.eventEntity;
import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.QEventHostEntity.eventHostEntity;
import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.QPackageEntity.packageEntity;
import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.QTicketEntity.ticketEntity;

@Repository
@RequiredArgsConstructor
public class JpaQueryPackageRepository {
    private final JPAQueryFactory queryFactory;

    public Page<EventPackageListDto> findAllByCondition(EventPackageQueryParam cond, Pageable pageable, Integer domainId) {
        QLanguageEntity l2 = new QLanguageEntity("l2");
        QLanguageEntity l3 = new QLanguageEntity("l3");

        LanguageType languageType = LanguageType.valueOf(RequestUtils.getCurLangCd().toUpperCase());

        JPAQuery<Long> count = queryFactory
                .select(packageEntity.countDistinct())
                .from(packageEntity)
                .leftJoin(languageEntity)
                .on(packageEntity.packageName.eq(languageEntity.languageId.languageCode).and(languageEntity.languageId.languageType.eq(languageType)))
                .leftJoin(ticketEntity)
                .on(packageEntity.packageId.eq(ticketEntity.ticketId.ticketId))
                .leftJoin(eventEntity)
                .on(packageEntity.eventId.eq(eventEntity.eventId))
                .leftJoin(l2)
                .on(eventEntity.eventName.eq(l2.languageId.languageCode).and(l2.languageId.languageType.eq(languageType)))
                .leftJoin(eventHostEntity)
                .on(eventEntity.eventId.eq(eventHostEntity.event.eventId))
                .leftJoin(domainEntity)
                .on(eventHostEntity.domain.domainId.eq(domainEntity.domainId))
                .leftJoin(l3)
                .on(domainEntity.domainName.eq(l3.languageId.languageCode).and(l3.languageId.languageType.eq(languageType)))
                .where(packageEntity.useYn.eq('y'),
                        searchPackageSearchDateType(cond.getDateType(), cond.getStartDateTime(), cond.getEndDateTime()),
                        searchPackageStateCode(cond.getStateOption()),
                        searchPackageSearchStaffType(cond.getStaffOption()),
                        searchPackageSearchNameAndCodeType(cond.getNameAndCodeType(), cond.getNameAndCodeKeyword(), l2),
                        searchPackageSearchDomainAndCodeType(cond.getDomainAndCodeType(), cond.getDomainAndCodeKeyword(), domainId, l3)
                );

        List<EventPackageListDto> content = queryFactory
                .select(new QEventPackageListDto(packageEntity.packageId,
                        ticketEntity.ticketOnlineCode.coalesce(TicketOnlineCode.ONLINE),
                        languageEntity.message.as("packageName"),
                        l2.message.as("eventName"),
                        packageEntity.packageType,
                        packageEntity.packageAuthorityCode.as("authorityCode"),
                        eventEntity,
                        packageEntity.price,
                        packageEntity.maxPackageCount,
                        packageEntity.ticketIds,
                        packageEntity.stateCode,
                        packageEntity.createDatetime)).distinct()
                .from(packageEntity)
                .leftJoin(languageEntity)
                .on(packageEntity.packageName.eq(languageEntity.languageId.languageCode).and(languageEntity.languageId.languageType.eq(languageType)))
                .leftJoin(ticketEntity)
                .on(packageEntity.packageId.eq(ticketEntity.ticketId.ticketId))
                .leftJoin(eventEntity)
                .on(packageEntity.eventId.eq(eventEntity.eventId))
                .leftJoin(l2)
                .on(eventEntity.eventName.eq(l2.languageId.languageCode).and(l2.languageId.languageType.eq(languageType)))
                .leftJoin(eventHostEntity)
                .on(eventEntity.eventId.eq(eventHostEntity.event.eventId))
                .leftJoin(domainEntity)
                .on(eventHostEntity.domain.domainId.eq(domainEntity.domainId))
                .leftJoin(l3)
                .on(domainEntity.domainName.eq(l3.languageId.languageCode).and(l3.languageId.languageType.eq(languageType)))
                .where(packageEntity.useYn.eq('y'),
                        searchPackageSearchDateType(cond.getDateType(), cond.getStartDateTime(), cond.getEndDateTime()),
                        searchPackageStateCode(cond.getStateOption()),
                        searchPackageSearchStaffType(cond.getStaffOption()),
                        searchPackageSearchNameAndCodeType(cond.getNameAndCodeType(), cond.getNameAndCodeKeyword(), l2),
                        searchPackageSearchDomainAndCodeType(cond.getDomainAndCodeType(), cond.getDomainAndCodeKeyword(), domainId, l3)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(packageEntity.createDatetime.desc())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private BooleanExpression searchPackageSearchDateType(PackageSearchDateType packageSearchDateType, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        BooleanExpression ex = null;

        if (!Objects.isNull(startDateTime) || !Objects.isNull(endDateTime)) {
            if (packageSearchDateType == PackageSearchDateType.EVENT_DATE) {
                ex = eventEntity.startDatetime.between(startDateTime, endDateTime);
            } else if (packageSearchDateType == PackageSearchDateType.CREATE_DATE) {
                ex = packageEntity.createDatetime.between(startDateTime, endDateTime);
            } else {
                ex = packageEntity.updateDatetime.between(startDateTime, endDateTime);
            }
        }

        return ex;
    }

    private BooleanExpression searchPackageStateCode(PackageStateCode packageStateCode) {
        BooleanExpression ex = null;

        if (packageStateCode == PackageStateCode.SALE) {
            ex = packageEntity.stateCode.eq(PackageStateCode.SALE);
        } else if (packageStateCode == PackageStateCode.STOP_SELLING) {
            ex = packageEntity.stateCode.eq(PackageStateCode.STOP_SELLING);
        }

        return ex;
    }

    private BooleanExpression searchPackageSearchStaffType(PackageSearchStaffType packageSearchStaffType) {
        BooleanExpression ex = null;

        if (packageSearchStaffType == PackageSearchStaffType.SPEAKER) {
            ex = packageEntity.packageAuthorityCode.eq(PackageAuthorityCode.SPEAKER);
        } else if (packageSearchStaffType == PackageSearchStaffType.STAFF) {
            ex = packageEntity.packageAuthorityCode.eq(PackageAuthorityCode.STAFF);
        } else if (packageSearchStaffType == PackageSearchStaffType.VISITOR) {
            ex = packageEntity.packageAuthorityCode.eq(PackageAuthorityCode.VISITOR);
        } else if (packageSearchStaffType == PackageSearchStaffType.OPERATOR) {
            ex = packageEntity.packageAuthorityCode.eq(PackageAuthorityCode.OPERATOR);
        }

        return ex;
    }

    private BooleanExpression searchPackageSearchNameAndCodeType(PackageSearchNameAndCodeType nameAndCodeType, String nameAndCodeKeyword, QLanguageEntity l) {
        BooleanExpression ex = null;

        if (StringUtils.hasText(nameAndCodeKeyword)) {
            if (nameAndCodeType == PackageSearchNameAndCodeType.EVENT_NAME) {
                ex = l.message.contains(nameAndCodeKeyword);
            } else if (nameAndCodeType == PackageSearchNameAndCodeType.EVENT_CODE) {
                ex = eventEntity.eventId.eq(Long.parseLong(nameAndCodeKeyword));
            } else if (nameAndCodeType == PackageSearchNameAndCodeType.PACKAGE_NAME) {
                ex = languageEntity.message.contains(nameAndCodeKeyword);
            } else if (nameAndCodeType == PackageSearchNameAndCodeType.PACKAGE_CODE) {
                ex = packageEntity.packageId.eq(nameAndCodeKeyword);
            }
        }

        return ex;
    }

    private BooleanExpression searchPackageSearchDomainAndCodeType(PackageSearchDomainAndCodeType domainAndCodeType, String domainAndCodeKeyword, Integer domainId, QLanguageEntity l) {
        BooleanExpression ex = null;

        if (StringUtils.hasText(domainAndCodeKeyword)) {
            if (domainAndCodeType == PackageSearchDomainAndCodeType.HOST_NAME) {
                ex = l.message.contains(domainAndCodeKeyword);
            } else if (domainAndCodeType == PackageSearchDomainAndCodeType.HOST_CODE) {
                ex = domainEntity.domainId.eq(Integer.parseInt(domainAndCodeKeyword));
            }

            if (!Objects.isNull(domainId) && domainId > 0) {
                ex = (ex == null) ? domainEntity.domainId.eq(domainId) : ex.and(domainEntity.domainId.eq(domainId));
            }
        }

        return ex;
    }
}
