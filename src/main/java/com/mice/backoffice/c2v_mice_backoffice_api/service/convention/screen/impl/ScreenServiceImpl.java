package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.DisplayCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenMappingType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.ScreenStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.screen.ScreenEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenDisplayGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.screen.ScreenUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.screen.ScreenRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen.ScreenDisplayService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.screen.ScreenService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ScreenServiceImpl implements ScreenService {
    // Service
    private final ScreenDisplayService screenDisplayService;

    // Repository
    private final ScreenRepository screenRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void create(List<ScreenCreateRequest> list, long mappingId, ScreenMappingType type) {
        if (Objects.nonNull(list)) {
            list.forEach(x -> {
                try {
                    create(x, mappingId, type);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    @Transactional
    public void create(ScreenCreateRequest req, long mappingId, ScreenMappingType type) throws IOException {
        if (Objects.nonNull(req) &&
                (req.getDisplayCode() != DisplayCode.ATTACHMENT || !isEmptyAttachment(req))
                ) {
                ScreenEntity se = ScreenEntity
                        .dataBuilder(req)
                        .mappingId(mappingId)
                        .mappingType(type)
                        .build();

                screenRepository.save(se);
                screenDisplayService.create(req.getScreenDisplays(), se);
        }
    }

    @Override
    @Transactional
    public void update(List<ScreenUpdateRequest> list, long mappingId, ScreenMappingType type) {
        List<ScreenCreateRequest> createList = new ArrayList<>();
        DisplayCode displayCode = list.get(0).getDisplayCode();

        List<Long> screenIds = list
                .stream()
                .map(ScreenUpdateRequest::getScreenId)
                .distinct()
                .toList();

        List<ScreenEntity> deleteScreenList =
                type.equals(ScreenMappingType.SESSION) && list.get(0).getSpaceObjectId() > 0
                        ? screenRepository.findAllByScreenIdNotAndSpaceObjectIdIsNull(mappingId, screenIds, displayCode)
                        : screenRepository.findAllByScreenIdNot(mappingId, screenIds, displayCode);

        deleteScreenList.forEach(ScreenEntity::delete);

        list.forEach(x -> {
            if (x.getScreenId() <= 0) {
                createList.add(ScreenCreateRequest.makeCreateRequest(x));
            } else {
                try {
                    update(x, mappingId, type);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        if (!createList.isEmpty())
            create(createList, mappingId, type);
    }

    @Override
    public void update(ScreenUpdateRequest req, long eventId, ScreenMappingType type) throws IOException {
        if (req.getScreenId() == 0) {
            ScreenCreateRequest screenCreateRequest = ScreenCreateRequest.makeCreateRequest(req);
            create(screenCreateRequest, eventId, type);
        } else {
            ScreenEntity screenEntity = screenRepository
                    .findById(req.getScreenId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("does not exist screenId: %s", req.getScreenId())));

            screenEntity.update(req);
            screenDisplayService.update(req.getScreenDisplays(), screenEntity);
        }
    }

    @Override
    public List<ScreenGetResponse> list(List<ScreenEntity> list) {
        List<ScreenGetResponse> result;

        if (Objects.isNull(list)) {
            result = new ArrayList<>();
        } else {
            result = list
                    .stream()
                    .filter(x -> x.getStateCode() != ScreenStateCode.DELETE)
                    .map(x -> {
                        List<ScreenDisplayGetResponse> screenDisplays = screenDisplayService.list(x.getScreenDisplays());

                        return ScreenGetResponse
                                .dataBuilder(x)
                                .screenDisplays(screenDisplays)
                                .build();
                    }).toList();
        }

        return result;
    }


    @Override
    public void deleteLounge(long loungeId) {
        List<ScreenEntity> screenEntityList = screenRepository.findAllByEventId(loungeId);
        for (ScreenEntity x : screenEntityList) {
            screenDisplayService.deleteLounge(x, x.getScreenId());

            ScreenEntity screenEntity = ScreenEntity.builder()
                    .mappingId(loungeId)
                    .mappingType(x.getMappingType())
                    .screenId(x.getScreenId())
                    .spaceId(x.getSpaceId())
                    .spaceObjectId(x.getSpaceObjectId())
                    .spaceObjectId(x.getSpaceObjectId())
                    .displayCode(x.getDisplayCode())
                    .stateCode(ScreenStateCode.DELETE)
                    .createUserId(x.getCreateUserId())
                    .createDatetime(x.getCreateDatetime())
                    .updateUserId(RequestUtils.getCurAdminId())
                    .updateDatetime(LocalDateTime.now())
                    .build();

            screenRepository.save(screenEntity);

        }
    }

    @Override
    public void updateLounge(List<ScreenUpdateRequest> list, long loungeNo, ScreenMappingType loungeType) throws IOException {
        deleteLounge(loungeNo);
        for(int i =0; i<list.size(); i++){
            ScreenCreateRequest screenCreateRequest = ScreenCreateRequest.makeCreateRequest(list.get(i));
            create(screenCreateRequest, loungeNo, loungeType);
        }
    }

    @Override
    public ScreenEntity save(ScreenEntity screenEntity) {
        screenRepository.save(screenEntity);

        return screenEntity;
    }

    private boolean isEmptyAttachment(ScreenCreateRequest req) {
        Map<LanguageType, String> fileNameMap = req.getScreenDisplays().get(0).getFileName();

        return (Objects.isNull(fileNameMap) ||
                Stream.of(LanguageType.values()).
                        allMatch(x -> fileNameMap.getOrDefault(x, "").isEmpty()));
    }
}
