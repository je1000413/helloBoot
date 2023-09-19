package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.DomainEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventHostEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventHostEntity.EventHostId;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventHostCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventHostGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventHostGetResponse.EventHostDetailGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.event.EventHostUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.DomainRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event.EventHostRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.EventHostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventHostServiceImpl implements EventHostService {
    // Service
    private final LanguageService languageService;

    // Repository
    private final EventHostRepository eventHostRepository;
    private final DomainRepository domainRepository;

    @Override
    @Transactional
    public void create(List<EventHostCreateRequest> list, long eventId) throws Exception {
        if (Objects.nonNull(list)) {
            list.forEach(ehr -> {
                DomainEntity domainEntity = domainRepository
                        .findById(ehr.getDomainId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("domainEntity does not exist domainId: %s", ehr.getDomainId())));

                EventHostId eventHostId = EventHostId
                        .builder()
                        .eventId(eventId)
                        .domainId(domainEntity.getDomainId())
                        .hostCode(ehr.getHostCode())
                        .build();

                EventHostEntity eventHostEntity = EventHostEntity
                        .dataBuilder(eventHostId)
                        .build();

                eventHostRepository.save(eventHostEntity);
            });
        }
    }

    @Override
    @Transactional
    public EventHostGetResponse getEventHostResponse(List<EventHostEntity> list) {
        String eventHostNames = getEventHostNames(list);

        List<EventHostDetailGetResponse> eventHostDetailGetList = list.stream().map(x -> {
            Map<LanguageType, String> domainNameMap = languageService.makeLanguageMap(x.getDomain().getDomainName());

            return EventHostDetailGetResponse
                    .dataBuilder(x)
                    .eventHostName(domainNameMap)
                    .build();
        }).toList();

        return EventHostGetResponse
                .builder()
                .eventHostNames(eventHostNames)
                .eventHosts(eventHostDetailGetList)
                .build();
    }

    @Override
    public Page<EventHostEntity> list(Pageable pageable) {
        return eventHostRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void update(List<EventHostUpdateRequest> list, long eventId) throws Exception {
        // 다 삭제 후 insert
        eventHostRepository.deleteAllByEventEventId(eventId);

        List<EventHostCreateRequest> createRequestList = list.stream().map(EventHostCreateRequest::makeCreateRequest).toList();

        create(createRequestList, eventId);
    }

    private String getEventHostNames(List<EventHostEntity> list) {
        LanguageType currentLangType = LanguageType.valueOf(RequestUtils.getCurLangCd().toUpperCase());

        return list
                .stream()
                .map(x -> {
                    String hostCodeName = "";

                    switch (x.getEventHostId().getHostCode()) {
                        case HOST -> hostCodeName = "주최사";
                        case AGENCY -> hostCodeName = "주관사";
                        case SPONSOR -> hostCodeName = "후원사";
                    }

                    List<LanguageEntity> languageList = languageService
                            .findAllByPkLanguageCode(x.getDomain().getDomainName());

                    LanguageEntity le = languageList
                            .stream()
                            .filter(e -> e.getLanguageType() == currentLangType)
                            .findFirst()
                            .orElseGet(() -> null);

                    return Objects.isNull(le) ? "" : String.format("(%s) %s", hostCodeName, le.getMessage());
                }).collect(Collectors.joining(", "));
    }
}
