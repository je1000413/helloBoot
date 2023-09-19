package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.*;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.NoticeBoardListDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.QNoticeBoardListDto;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.NoticeBoardQueryParam;
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

import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.QLanguageEntity.languageEntity;
import static com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.QNoticeBoardEntity.noticeBoardEntity;

@Repository
@RequiredArgsConstructor
public class JpaQueryNoticeBoardRepository {
    private final JPAQueryFactory queryFactory;

    public Page<NoticeBoardListDto> findAllByCondition(NoticeBoardQueryParam cond, Pageable pageable) {
        LanguageType languageType = LanguageType.valueOf(RequestUtils.getCurLangCd().toUpperCase());
        JPAQuery<Long> count = queryFactory
                .select(noticeBoardEntity.count())
                .from(noticeBoardEntity)
                .leftJoin(languageEntity)
                .on(noticeBoardEntity.articleTitle.eq(languageEntity.languageId.languageCode).and(languageEntity.languageId.languageType.eq(languageType)))
                .where(noticeBoardEntity.useYn.eq('y'),
                        searchNoticeBoardSearchDateType(cond.getDateType(), cond.getStartDateTime(), cond.getEndDateTime()),
                        searchNoticeBoardSearchArticleType(cond.getArticleType()),
                        searchNoticeBoardSearchRollingType(cond.getRollingType()),
                        searchNoticeBoardSearchTitleAndCreatorType(cond.getTitleAndCreatorType(), cond.getTitleAndCreatorKeyword())
                );

        List<NoticeBoardListDto> content = queryFactory
                .select(new QNoticeBoardListDto(
                        noticeBoardEntity.boardSeq,
                        noticeBoardEntity.articleType,
                        languageEntity.message,
                        noticeBoardEntity.mainYn,
                        noticeBoardEntity.createUser,
                        noticeBoardEntity.updateUser,
                        noticeBoardEntity.updateDatetime
                ))
                .from(noticeBoardEntity)
                .leftJoin(languageEntity)
                .on(noticeBoardEntity.articleTitle.eq(languageEntity.languageId.languageCode).and(languageEntity.languageId.languageType.eq(languageType)))
                .where(noticeBoardEntity.useYn.eq('y'),
                        searchNoticeBoardSearchDateType(cond.getDateType(), cond.getStartDateTime(), cond.getEndDateTime()),
                        searchNoticeBoardSearchArticleType(cond.getArticleType()),
                        searchNoticeBoardSearchRollingType(cond.getRollingType()),
                        searchNoticeBoardSearchTitleAndCreatorType(cond.getTitleAndCreatorType(), cond.getTitleAndCreatorKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(noticeBoardEntity.boardSeq.desc())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private BooleanExpression searchNoticeBoardSearchDateType(NoticeBoardSearchDateType dateType, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        BooleanExpression ex = null;

        if (!Objects.isNull(startDateTime) && !Objects.isNull(endDateTime)) {
            ex = (dateType == NoticeBoardSearchDateType.UPDATE_DATE) ? noticeBoardEntity.updateDatetime.between(startDateTime, endDateTime) : noticeBoardEntity.createDatetime.between(startDateTime, endDateTime);
        }

        return ex;
    }

    private BooleanExpression searchNoticeBoardSearchArticleType(NoticeBoardSearchArticleType articleType) {
        BooleanExpression ex = null;

        if (articleType == NoticeBoardSearchArticleType.INTEGRATED) {
            ex = noticeBoardEntity.articleType.eq(NoticeBoardArticleType.INTEGRATED);
        } else if (articleType == NoticeBoardSearchArticleType.NORMAL) {
            ex = noticeBoardEntity.articleType.eq(NoticeBoardArticleType.NORMAL);
        }

        return ex;
    }

    private BooleanExpression searchNoticeBoardSearchRollingType(NoticeBoardSearchRollingType rollingType) {
        BooleanExpression ex = null;

        if (rollingType == NoticeBoardSearchRollingType.ROLLING) {
            ex = noticeBoardEntity.mainYn.eq('y');
        } else if (rollingType == NoticeBoardSearchRollingType.NONE_ROLLING) {
            ex = noticeBoardEntity.mainYn.eq('n');
        }

        return ex;
    }

    private BooleanExpression searchNoticeBoardSearchTitleAndCreatorType(NoticeBoardSearchTitleAndCreatorType titleAndCreatorType, String titleAndCreatorKeyword) {
        BooleanExpression ex = null;

        if (StringUtils.hasText(titleAndCreatorKeyword)) {
            if (titleAndCreatorType == NoticeBoardSearchTitleAndCreatorType.TITLE) {
                ex = languageEntity.message.eq(titleAndCreatorKeyword);
            } else if (titleAndCreatorType == NoticeBoardSearchTitleAndCreatorType.UPDATE_USER_NAME) {
                ex = noticeBoardEntity.updateUser.name.eq(titleAndCreatorKeyword);
            } else {
                ex = noticeBoardEntity.updateUserId.eq(Long.parseLong(titleAndCreatorKeyword));
            }
        }

        return ex;
    }
}
