package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.impl;

import com.google.gson.Gson;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageAuthorityCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackagePriceType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageType;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.PackageGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.TicketGetDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.PackageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.TicketEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session.PackageRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.PackageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.TicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PackageServiceImpl implements PackageService {
    // Service
    private final TicketService ticketService;
    private final LanguageService languageService;

    // Repository
    private final PackageRepository packageRepository;

    // json
    private final Gson gson;

    @Override
    public XPackageGetResponse get(List<PackageEntity> packageList, List<TicketEntity> ticketList) {
        PackageEntity pe = packageList.get(0);
        Map<LanguageType, String> packageNameMap = languageService.makeLanguageMap(pe.getPackageName());

        XPackageGetResponse result = XPackageGetResponse
                .dataBuilder(pe)
                .packageName(packageNameMap)
                .build();

        String ticketId = ticketList
                .get(0)
                .getTicketId()
                .getTicketId();

        packageList
                .stream()
                .filter(x -> {
                    Map<String, List<String>> ticketIds = gson.fromJson(x.getTicketIds(), Map.class);
                    List<String> ticketIdList = ticketIds.get("ticket_Ids");

                    return ticketIdList
                            .stream()
                            .anyMatch(e -> e.equalsIgnoreCase(ticketId))
                            && x.getPackageAuthorityCode() == PackageAuthorityCode.DEFAULT
                            && ticketIdList.size() == 1;
                }).forEach(x -> {
                    PackagePriceType packagePriceType;

                    Map<String, List<String>> ticketIds = gson.fromJson(x.getTicketIds(), Map.class);
                    List<String> ticketIdList = ticketIds.get("ticket_Ids");

                    if (x.getPrice() == 0) {
                        packagePriceType = PackagePriceType.FREE;
                    } else if (x.getPrice() > 0 && packageList.size() > 2) {
                        packagePriceType = PackagePriceType.PAYMENT_DIFFERENTIAL;
                    } else {
                        packagePriceType = PackagePriceType.PAYMENT_EQUAL;
                    }

                    String id = ticketIdList.get(0);

                    TicketGetResponse ticketGetResponse = ticketService.get(ticketList, id);
                    ticketGetResponse.setPrice(x.getPrice());
                    ticketGetResponse.setMaxTicketCount(x.getMaxPackageCount());
                    ticketGetResponse.setPackagePriceType(packagePriceType);

                    result.addTicket(ticketGetResponse);
                    result.addPackageId(x.getPackageId());
                });
        return result;
    }

    @Override
    public List<PackageGetResponse> list(long sessionId) {
        List<TicketGetDto> ticketList = ticketService.list(sessionId);
        return ticketList
                .stream()
                .map(x -> {
                    PackageGetResponse packageGetResponse = PackageGetResponse.makeGetResponse(x);
                    Map<LanguageType, String> packageNameMap = languageService.makeLanguageMap(x.getPackageNameKey());

                    packageGetResponse.setPackageName(packageNameMap);

                    return packageGetResponse;
                }).toList();
    }

    @Override
    @Transactional
    public void create(List<PackageCreateRequest> list, SessionEntity se, long eventId) {
        list.forEach(x -> {
            String seq = SequenceUtils.getSequence();
            TicketEntity ticketEntity = ticketService.create(x.getTicket(), se);

            Map<String, Map<LanguageType, String>> messageMap = makeLanguageHashMap(seq, x);
            List<LanguageEntity> languageList = languageService.makeLanguageEntities(messageMap);

            languageService.saveAll(languageList);

            PackageEntity pe = PackageEntity
                    .dataBuilder(x.getTicket())
                    .packageId(ticketEntity.getTicketId().getTicketId())
                    .packageType(PackageType.ENTRANCE_TO_SESSION)
                    .packageName(String.format("package_name_%s", seq))
                    .ticketIds(String.format("{\"ticket_Ids\": [\"%s\"]}", ticketEntity.getTicketId().getTicketId()))
                    .eventId(eventId)
                    .startDatetime(se.getProgram().getEvent().getStartDatetime())
                    .endDatetime(se.getProgram().getEvent().getEndDatetime())
                    .build();

            packageRepository.save(pe);
        });
    }

    @Override
    @Transactional
    public void delete(PackageUpdateRequest packageInfo, SessionEntity se, long eventId) {
        // 기존에 존재 하는 데이터 삭제하고 다시 추가
        /*List<PackageEntity> packageList = packageRepository.findAllById(packageInfo.getPackageIds());
        packageList.forEach(x -> {
            // 다국어 삭제
            languageService.deleteByLanguageCode(x.getPackageName());
            x.delete();
        });

        // ticket 삭제
        se.getTickets().forEach(ticketService::delete);*/
    }

    @Override
    @Transactional
    public void update(List<PackageUpdateRequest> packages, SessionEntity se, long eventId) {
        // 새로 생성 되어야 할 상품
        List<PackageCreateRequest> packageCreateList = new ArrayList<>();

        // 기존에 존재하는 상품 id 리스트
        List<String> packageIdList = new ArrayList<>();

        packages.forEach(x -> {
            if (x.getPackageId() == null || x.getPackageId().isBlank()) {
                packageCreateList.add(PackageCreateRequest.makeCreateRequest(x));
            } else {
                // 기존 상품 업데이트
                ticketService.update(x.getTicket(), se);
                PackageEntity pe = packageRepository
                        .findById(x.getPackageId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format("package entity does not exist packageId: %s", x.getPackageId())));

                pe.update(x.getTicket());
                languageService.updateLanguageEntityList(x.getPackageName(), pe.getPackageName());

                packageIdList.add(x.getPackageId());
            }
        });

        // 기존에 존재 하였으나, 지금은 없는 항목에 대해서 삭제처리
        // 삭제 된 항목의 id return
        List<String> deletedIdList = ticketService.deleteExceptIdList(packageIdList, se.getSessionId());

        // 전달 받은 id를 package 항목에서도 delete ticket, package id는 동일하다.
        packageRepository.deleteAllById(deletedIdList);

        // 새로 추가된 항목에 대해서 create
        if (!packageCreateList.isEmpty())
            create(packageCreateList, se, eventId);
    }

    private Map<String, Map<LanguageType,String>> makeLanguageHashMap(String seq, PackageCreateRequest pr) {
        Map<String, Map<LanguageType,String>> languageMap = new HashMap<>();

        languageMap.put(String.format("package_name_%s", seq), pr.getPackageName());

        return languageMap;
    }
}
