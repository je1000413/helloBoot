package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.impl;

import com.com2verse.platform.exception.C2VException;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RowNumUtil;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.NoticeBoardListDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.NoticeBoardEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.NoticeBoardRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl.JpaQueryNoticeBoardRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.NoticeBoardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService {
    // Service
    private final LanguageService languageService;

    // Repository
    private final NoticeBoardRepository noticeBoardRepository;
    private final JpaQueryNoticeBoardRepository jpaQueryNoticeBoardRepository;

    @Override
    public NoticeBoardListResponse list(NoticeBoardQueryParam cond) throws C2VException {
        PageRequest pageRequest = PageRequest.of(cond.getPageNum() - 1, cond.getPageSize());
        Page<NoticeBoardListDto> page = jpaQueryNoticeBoardRepository.findAllByCondition(cond, pageRequest);

        RowNumUtil rowNumUtil = new RowNumUtil(page.getTotalElements(), cond.getPageNum(), pageRequest.getPageSize());

        List<NoticeBoardListDetailResponse> noticeBoards = page
                .getContent()
                .stream()
                .map(x -> {
                    String userName = String.format("%s (%s)", x.getUpdateUser().getName(), x.getUpdateUser().getAccountName());
                    LocalDateTime dateTime = x.getUpdateDatetime();

                    return NoticeBoardListDetailResponse
                            .dataBuilder(x)
                            .itemNo(rowNumUtil.getRowNum())
                            .userName(userName)
                            .dateTime(dateTime)
                            .build();
                }).toList();

        return NoticeBoardListResponse
                .dataBuilder(page)
                .noticeBoards(noticeBoards)
                .build();
    }

    @Override
    @Transactional
    public NoticeBoardGetResponse get(int boardSeq) throws C2VException {
        NoticeBoardEntity nbe = findByBoardSeq(boardSeq);

        Map<LanguageType, String> articleTitleMap = languageService.makeLanguageMap(nbe.getArticleTitle());
        Map<LanguageType, String> articleDescriptionMap = languageService.makeLanguageMap(nbe.getArticleDescription());

        return NoticeBoardGetResponse
                .dataBuilder(nbe)
                .articleTitle(articleTitleMap)
                .articleDescription(articleDescriptionMap)
                .build();
    }

    @Override
    @Transactional
    public NoticeBoardCreateResponse create(NoticeBoardCreateRequest req) throws C2VException {
        String seq = SequenceUtils.getSequence();
        Map<String, Map<LanguageType, String>> messageMap = makeLanguageHashMap(req, seq);

        List<LanguageEntity> languageList = languageService.makeLanguageEntities(messageMap);
        languageService.saveAll(languageList);

        NoticeBoardEntity nbe = NoticeBoardEntity
                .dataBuilder(req)
                .articleTitle(String.format("article_title_%s", seq))
                .articleDescription(String.format("article_description_%s", seq))
                .build();

        return new NoticeBoardCreateResponse(noticeBoardRepository.save(nbe).getBoardSeq());
    }

    @Override
    @Transactional
    public void update(NoticeBoardUpdateRequest req, int boardSeq) throws C2VException {
        NoticeBoardEntity nbe = findByBoardSeq(boardSeq);

        nbe.update(req);

        languageService.updateLanguageEntityList(req.getArticleTitle(), nbe.getArticleTitle());
        languageService.updateLanguageEntityList(req.getArticleDescription(), nbe.getArticleDescription());
    }

    @Override
    @Transactional
    public void delete(int boardSeq) throws C2VException {
        NoticeBoardEntity nbe = findByBoardSeq(boardSeq);

        nbe.delete();
    }

    @Transactional
    public NoticeBoardEntity findByBoardSeq(int boardSeq) {
        return noticeBoardRepository
                .findById(boardSeq)
                .orElseThrow(() -> new EntityNotFoundException(String.format("noticeBoard entity does not exist boardSeq: %s", boardSeq)));
    }

    private Map<String, Map<LanguageType,String>> makeLanguageHashMap(NoticeBoardCreateRequest req, String seq) {
        Map<String, Map<LanguageType, String>> languageMap = new HashMap<>();

        languageMap.put(String.format("article_title_%s", seq), req.getArticleTitle());
        languageMap.put(String.format("article_description_%s", seq), req.getArticleDescription());

        return languageMap;
    }
}
