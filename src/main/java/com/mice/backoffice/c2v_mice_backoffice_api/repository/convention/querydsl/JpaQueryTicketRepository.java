package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.QTicketGetDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.TicketGetDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.QTicketEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.TicketEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.QPackageEntity.packageEntity;
import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.QTicketEntity.ticketEntity;

@Repository
@RequiredArgsConstructor
public class JpaQueryTicketRepository {
    private final JPAQueryFactory queryFactory;

    public List<TicketGetDto> getTicketListBySessionId(long sessionId) {
        return queryFactory
            .select(new QTicketGetDto(ticketEntity.ticketId.ticketId,
                                ticketEntity.ticketName.as("ticketNameKey"),
                                packageEntity.packageName.as("packageNameKey"),
                                packageEntity.price,
                                ticketEntity.ticketOnlineCode,
                                packageEntity.maxPackageCount,
                                packageEntity.packageType,
                                packageEntity.createUserId,
                                packageEntity.updateUserId))
                .from(ticketEntity)
                .innerJoin(packageEntity)
                .on(ticketEntity.ticketId.ticketId.eq(packageEntity.packageId))
                .where(ticketEntity.ticketId.session.sessionId.eq(sessionId)
                        .and(packageEntity.useYn.eq('y')))
                .fetch();
    }

    public List<TicketEntity> findAllByTicketIds(List<String> ticketIds) {
        return queryFactory
                .selectFrom(ticketEntity)
                .where(ticketEntity.ticketId.ticketId.in(ticketIds))
                .fetch();
    }

}
