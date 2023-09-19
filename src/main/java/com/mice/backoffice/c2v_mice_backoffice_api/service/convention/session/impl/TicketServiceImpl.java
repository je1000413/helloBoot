package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.TicketGetDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.TicketEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.TicketEntity.TicketId;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketGetResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.TicketUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl.JpaQueryTicketRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session.TicketRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.TicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    // Service
    private final LanguageService languageService;

    // Repository
    private final TicketRepository ticketRepository;
    private final JpaQueryTicketRepository jpaQueryTicketRepository;

    private final String PREFIX = "tc";
    private final String PROMOTION_PREFIX = "tf";

    @Override
    public TicketGetResponse get(List<TicketEntity> list, String ticketId) {
        return list
                .stream()
                .filter(x -> x.getTicketId()
                        .getTicketId()
                        .equalsIgnoreCase(ticketId))
                .map(x -> {
                    Map<LanguageType, String> ticketNameMap = languageService.makeLanguageMap(x.getTicketName());

                    return TicketGetResponse
                            .dataBuilder(x)
                            .ticketName(ticketNameMap)
                            .build();
                })
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format("ticket Entity does not exist ticketId: %s", ticketId)));
    }

    @Override
    @Transactional
    public List<TicketGetDto> list(long sessionId) {
        List<TicketGetDto> ticketList = jpaQueryTicketRepository.getTicketListBySessionId(sessionId);
        ticketList.forEach(x -> x.setTicketName(languageService.makeLanguageMap(x.getTicketNameKey())));

        return ticketList;
    }

    @Override
    public List<TicketEntity> findAllByTicketIds(List<String> ticketIds) {
        return jpaQueryTicketRepository.findAllByTicketIds(ticketIds);
    }

    @Override
    @Transactional
    public TicketEntity create(TicketCreateRequest req, SessionEntity se) {
        String seq = SequenceUtils.getSequence();
        String ticketSeq = ticketRepository.findTicketId();

        Map<String, Map<LanguageType, String>> messageMap = makeLanguageHashMap(seq, req);
        List<LanguageEntity> languageList = languageService.makeLanguageEntities(messageMap);

        languageService.saveAll(languageList);

        String prefix = req.getPrice() <= 0 ? PROMOTION_PREFIX : PREFIX;

        TicketId ticketId = TicketId
                .builder()
                .ticketId(String.format("%s-%s", prefix, ticketSeq))
                .session(se)
                .build();

        TicketEntity te = TicketEntity
                .dataBuilder(req)
                .ticketName(String.format("ticket_name_%s", seq))
                .ticketId(ticketId)
                .build();

        ticketRepository.save(te);

        return te;
    }

    @Override
    @Transactional
    public void delete(TicketEntity te) {
        languageService.deleteByLanguageCode(te.getTicketName());
        ticketRepository.deleteById(te.getTicketId());
    }

    @Override
    @Transactional
    public void update(TicketUpdateRequest req, SessionEntity se) {
        TicketId ticketId = TicketId
                .builder()
                .ticketId(req.getTicketId())
                .session(se)
                .build();

        TicketEntity te = ticketRepository
                .findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("ticket entity does not exist ticketId: %s", req.getTicketId())));

        te.update(req);
        languageService.updateLanguageEntityList(req.getTicketName(), te.getTicketName());

    }

    @Override
    @Transactional
    public List<String> deleteExceptIdList(List<String> ticketIds, long sessionId) {
        List<TicketEntity> ticketList = ticketRepository.findAllByTicketIdNotInSessionId(ticketIds, sessionId);
        ticketList.forEach(this::delete);

        return ticketList
                .stream()
                .map(x -> x.getTicketId().getTicketId())
                .toList();
    }

    private Map<String, Map<LanguageType,String>> makeLanguageHashMap(String seq, TicketCreateRequest tr) {
        Map<String, Map<LanguageType,String>> languageMap = new HashMap<>();

        languageMap.put(String.format("ticket_name_%s", seq), tr.getTicketName() );

        return languageMap;
    }
}
