package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.eventpackage.impl;

import com.com2verse.platform.exception.C2VException;
import com.google.gson.Gson;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RowNumUtil;
import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.TicketOnlineCode;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.event.EventPackageDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.event.EventPackageListDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.account.AccountEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.event.EventEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.PackageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.TicketEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage.*;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage.EventPackageGetResponse.ProgramGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage.EventPackageGetResponse.SessionGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.AdminRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.AccountRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl.JpaQueryPackageRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session.PackageRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.event.EventService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.eventpackage.EventPackageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.program.ProgramService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.TicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.*;

@Service
@AllArgsConstructor
public class EventPackageServiceImpl implements EventPackageService {
    // Service
    private final LanguageService languageService;
    private final EventService eventService;
    private final ProgramService programService;
    private final SessionService sessionService;
    private final TicketService ticketService;

    // Repository
    private final PackageRepository packageRepository;
    private final AccountRepository accountRepository;
    private final AdminRepository adminRepository;
    private final JpaQueryPackageRepository jpaQueryPackageRepository;

    // json
    private final Gson gson;

    private final String PREFIX = "pc";
    private final String PROMOTION_PREFIX = "pf";
    private final String KEY = "ticket_Ids";

    @Override
    public EventPackageListResponse list(EventPackageQueryParam cond) throws Exception {
        PageRequest pageRequest = PageRequest.of(cond.getPageNum() - 1, cond.getPageSize());
        boolean isCom2verseUser = false;
        Integer domainId = null;

        Long userId = RequestUtils.getCurAdminId();

        Optional<AdminEntity> adminOptional = adminRepository.findById(userId);

        if (adminOptional.isPresent()) {
            isCom2verseUser = true;
        } else {
            AccountEntity accountEntity = accountRepository
                    .findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("user entity does not exist userId: %s", userId)));

            domainId = accountEntity.getDomainId();
        }

        Page<EventPackageListDto> page = jpaQueryPackageRepository.findAllByCondition(cond, pageRequest, domainId);
        LanguageType currentLanguageType = LanguageType.valueOf(RequestUtils.getCurLangCd().toUpperCase());

        RowNumUtil rowNumUtil = new RowNumUtil(page.getTotalElements(), cond.getPageNum(), pageRequest.getPageSize());

        List<EventPackageListDetailResponse> packageList = page.getContent()
                .stream()
                .map(x -> {
                    List<String> programNames = new ArrayList<>();
                    List<String> sessionNames = new ArrayList<>();

                    if (x.getPackageType() != PackageType.ENTRANCE_ALL_IN_EVENT) {
                        Map<String, List<String>> ticketIdMap = gson.fromJson(x.getTicketIds(), Map.class);
                        List<String> ticketIdList  = ticketIdMap.get(KEY);

                        // ticketEntity List get
                        List<TicketEntity> ticketList = ticketService.findAllByTicketIds(ticketIdList);

                        // 프로그램 내 모든 세션 입장
                        if (x.getPackageType() == PackageType.ENTRANCE_ALL_IN_PROGRAM) {
                            TicketEntity ticketEntity = ticketList.get(0);

                            // program entity get
                            ProgramEntity programEntity = ticketEntity.getTicketId().getSession().getProgram();

                            Map<LanguageType, String> programNameMap = languageService.makeLanguageMap(programEntity.getProgramName());
                            String programName = programNameMap.get(currentLanguageType);

                            programNames.add(programName);
                            sessionNames.add("ALL");
                        }
                        // 지정 세션 입장
                        else if (x.getPackageType() == PackageType.ENTRANCE_TO_SESSION) {
                            // 지정 세션이므로, 여러개의 프로그램이 존재 할 수 있음
                            Map<Long, List<ProgramEntity>> programMap = ticketList
                                    .stream()
                                    .map(e -> e.getTicketId()
                                            .getSession()
                                            .getProgram())
                                    .collect(Collectors.groupingBy(ProgramEntity::getProgramId));

                            List<SessionEntity> sessionList = ticketList
                                    .stream()
                                    .map(e -> e
                                            .getTicketId()
                                            .getSession())
                                    .toList();

                            for (Map.Entry<Long, List<ProgramEntity>> k : programMap.entrySet()) {
                                ProgramEntity programEntity = k.getValue().get(0);
                                Map<LanguageType, String> programNameMap = languageService.makeLanguageMap(programEntity.getProgramName());

                                String programName = programNameMap.get(currentLanguageType);

                                programNames.add(programName);
                            }

                            sessionList.forEach(e -> {
                                Map<LanguageType, String> sessionNameMap = languageService.makeLanguageMap(e.getSessionName());
                                String sessionName = sessionNameMap.get(currentLanguageType);

                                sessionNames.add(sessionName);
                            });
                        }
                    } else {
                        programNames.add("ALL");
                        sessionNames.add("ALL");
                    }

                    return EventPackageListDetailResponse
                            .dataBuilder(x)
                            .itemNo(rowNumUtil.getRowNum())
                            .programName(String.join(",", programNames))
                            .sessionName(String.join(",", sessionNames))
                            .build();
                }).toList();

        return EventPackageListResponse
                .dataBuilder(page)
                .domainId(domainId)
                .isCom2verseUser(isCom2verseUser)
                .packages(packageList)
                .build();
    }

    @Override
    @Transactional
    public EventPackageGetResponse get(String packageId) throws Exception {
        List<String> programNames = new ArrayList<>();
        List<String> sessionNames = new ArrayList<>();

        Set<ProgramGetResponse> programs = new HashSet<>();

        PackageEntity pe = findByPackageId(packageId);

        Map<LanguageType, String> packageNameMap = languageService.makeLanguageMap(pe.getPackageName());
        Map<LanguageType, String> eventNameMap = languageService.makeLanguageMap(pe.getEvent().getEventName());

        LanguageType currentLanguageType = LanguageType.valueOf(RequestUtils.getCurLangCd().toUpperCase());

        // 프로그램 전체 입장 or 지정 세션 입장일 경우
        if (pe.getPackageType() != PackageType.ENTRANCE_ALL_IN_EVENT) {
            Map<String, List<String>> ticketIdMap = gson.fromJson(pe.getTicketIds(), Map.class);
            List<String> ticketIdList  = ticketIdMap.get(KEY);

            List<TicketEntity> ticketList = ticketService.findAllByTicketIds(ticketIdList);

            // 프로그램 전체 입장일 경우
            if (pe.getPackageType() == PackageType.ENTRANCE_ALL_IN_PROGRAM) {
                TicketEntity ticketEntity = ticketList.get(0);
                ProgramEntity programEntity = ticketEntity.getTicketId().getSession().getProgram();

                Map<LanguageType, String> programNameMap = languageService.makeLanguageMap(programEntity.getProgramName());
                String programName = programNameMap.get(currentLanguageType);

                programNames.add(programName);

                ProgramGetResponse programGetResponse = ProgramGetResponse
                        .builder()
                        .programName(programNameMap)
                        .programId(programEntity.getProgramId())
                        .build();

                programs.add(programGetResponse);
            } else if (pe.getPackageType() == PackageType.ENTRANCE_TO_SESSION) {
                Map<Long, ProgramGetResponse> programGetResponseMap = new HashMap<>();

                Map<Long, List<ProgramEntity>> programMap = ticketList
                        .stream()
                        .map(x -> x.getTicketId()
                                .getSession()
                                .getProgram())
                        .collect(Collectors.groupingBy(ProgramEntity::getProgramId));

                List<SessionEntity> sessionList = ticketList
                        .stream()
                        .map(x -> x
                                .getTicketId()
                                .getSession())
                        .toList();

                for (Map.Entry<Long, List<ProgramEntity>> k : programMap.entrySet()) {
                    ProgramEntity programEntity = k.getValue().get(0);
                    Map<LanguageType, String> programNameMap = languageService.makeLanguageMap(programEntity.getProgramName());

                    String programName = programNameMap.get(currentLanguageType);

                    programNames.add(programName);

                    ProgramGetResponse programGetResponse = ProgramGetResponse
                            .builder()
                            .programName(programNameMap)
                            .sessions(new HashSet<>())
                            .programId(programEntity.getProgramId())
                            .build();

                    programGetResponseMap.put(k.getKey(), programGetResponse);
                }

                sessionList.forEach(x -> {
                    long programId = x.getProgram().getProgramId();
                    ProgramGetResponse programGetResponse = programGetResponseMap.get(programId);

                    Map<LanguageType, String> sessionNameMap = languageService.makeLanguageMap(x.getSessionName());
                    String sessionName = sessionNameMap.get(currentLanguageType);

                    sessionNames.add(sessionName);

                    SessionGetResponse sessionGetResponse = SessionGetResponse
                            .builder()
                            .sessionName(sessionNameMap)
                            .sessionId(x.getSessionId())
                            .build();

                    programGetResponse.getSessions().add(sessionGetResponse);
                });

            }
        } else {
            programNames.add("ALL");
            sessionNames.add("ALL");
        }


        return EventPackageGetResponse
                .dataBuilder(pe)
                .eventName(eventNameMap)
                .packageName(packageNameMap)
                .programNames(String.join(",", programNames))
                .programs(programs)
                .sessionNames(String.join(",", sessionNames))
                .build();
    }

    @Override
    @Transactional
    public EventPackageCreateResponse create(EventPackageCreateRequest req) throws Exception {
        String seq = SequenceUtils.getSequence();
        String packageSeq = packageRepository.findPackageId();

        Map<String, Map<LanguageType, String>> messageMap = makeLanguageHashMap(seq, req);
        List<LanguageEntity> languageList = languageService.makeLanguageEntities(messageMap);

        languageService.saveAll(languageList);

        EventEntity ee = eventService.findByEventId(req.getEventId());
        EventPackageDto dto = EventPackageDto.makeInstance(req);

        setTicketIdList(dto, ee);

        String prefix = req.getPrice() <= 0 ? PROMOTION_PREFIX : PREFIX;

        PackageEntity pe = PackageEntity
                .dataBuilder(req)
                .ticketIds(makeTicketIds(dto.getTicketIdList()))
                .startDatetime(ee.getStartDatetime())
                .endDatetime(ee.getEndDatetime())
                .packageId(String.format("%s-%s", prefix, packageSeq))
                .packageName(String.format("package_name_%s", seq))
                .build();

        packageRepository.save(pe);

        return EventPackageCreateResponse
                .builder()
                .packageId(pe.getPackageId())
                .build();
    }

    @Override
    @Transactional
    public void update(String packageId, EventPackageUpdateRequest req) throws Exception {
        PackageEntity pe = findByPackageId(packageId);

        EventEntity ee = eventService.findByEventId(req.getEventId());
        EventPackageDto dto = EventPackageDto.makeInstance(req);

        setTicketIdList(dto, ee);

        // 다국어 컬럼 업데이트
        languageService.updateLanguageEntityList(req.getPackageName(), pe.getPackageName());

        pe.update(dto);
        pe.updateTicketIds(makeTicketIds(dto.getTicketIdList()));
    }

    @Override
    @Transactional
    public void changePackageType(String packageId, EventPackagePatchRequest req) throws C2VException {
        PackageEntity pe = findByPackageId(packageId);

        pe.changePackageStateCode(req.getPackageStateCode());
    }

    @Override
    @Transactional
    public void delete(String packageId) throws Exception {
        PackageEntity pe = findByPackageId(packageId);

        pe.delete();
    }

    private void setTicketIdList(EventPackageDto dto, EventEntity ee) throws Exception {
        List<String> ticketIdList = null;
        List<SessionEntity> sessionList = new ArrayList<>();
        switch (dto.getPackageType()) {

            case ENTRANCE_ALL_IN_EVENT ->
                sessionList = ee.getPrograms()
                        .stream()
                        .filter(x -> Character.toLowerCase(x.getUseYn()) == 'y')
                        .flatMap(x -> x.getSessions()
                                .stream()
                                .filter(e -> Character.toLowerCase(e.getUseYn()) == 'y')
                        ).toList();
            case ENTRANCE_ALL_IN_PROGRAM -> {
                ProgramEntity pe = programService.findByProgramId(dto.getProgramId());

                sessionList = pe.getSessions()
                        .stream()
                        .filter(x -> Character.toLowerCase(x.getUseYn()) == 'y')
                        .toList();
            } case ENTRANCE_TO_SESSION ->
                sessionList = sessionService.findAllBySessionIds(dto.getSessionIds());
            default -> {
            }
        }
        ticketIdList = sessionList
                .stream()
                .flatMap(x -> x.getTickets()
                        .stream()
                        .filter(v -> v.getTicketOnlineCode() == TicketOnlineCode.ONLINE)
                        .map(e -> e.getTicketId().getTicketId()))
                .toList();

        dto.setTicketIdList(ticketIdList);
    }

    @Transactional
    public PackageEntity findByPackageId(String packageId) {
        return packageRepository
                .findById(packageId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("package entity does not exist packageId: %s", packageId)));
    }

    private String makeTicketIds(List<String> ticketIdList) {
        Map<String ,List<String>> map = new HashMap<>();

        map.put(KEY, ticketIdList);
        return gson.toJson(map);
    }

    private Map<String, Map<LanguageType,String>> makeLanguageHashMap(String seq, EventPackageCreateRequest req) {
        Map<String, Map<LanguageType,String>> languageMap = new HashMap<>();

        languageMap.put(String.format("package_name_%s", seq), req.getPackageName());

        return languageMap;
    }
}
