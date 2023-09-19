package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.common.SequenceUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.OperatorSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.OperatorSupports.CommandType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionQuestionStateCode;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.SessionQuestionListDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.SessionQuestionEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.session.*;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl.JpaQuerySessionQuestionRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session.SessionQuestionRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.session.SessionQuestionService;
import com.mice.backoffice.c2v_mice_backoffice_api.service.operator.RedisPubService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SessionQuestionServiceImpl implements SessionQuestionService {
    // Service
    private final RedisPubService redisPubService;

    // Repository
    private final SessionQuestionRepository sessionQuestionRepository;
    private final JpaQuerySessionQuestionRepository jpaQuerySessionQuestionRepository;

    @Override
    public SessionQuestionListResponse list(SessionQuestionListQueryParam param, long sessionId) throws Exception {
        PageRequest pageRequest = PageRequest.of(param.getPageNum() - 1, param.getPageSize());

        Page<SessionQuestionListDto> page = jpaQuerySessionQuestionRepository.findAllByCondition(param, pageRequest, sessionId);

        List<SessionQuestionDetailListResponse> questions = page.getContent()
                .stream()
                .map(SessionQuestionDetailListResponse::makeInstance)
                .toList();

        return SessionQuestionListResponse
                .builder()
                .questions(questions)
                .totalCount(page.getTotalElements())
                .build();
    }


    @Override
    public void list(SessionQuestionUserListQueryParam param, long sessionId) throws Exception {

    }

    @Override
    @Transactional
    public void changeState(SessionQuestionStateRequest req, long sessionId, int questionSeq) throws Exception {
        SessionQuestionEntity sqe = sessionQuestionRepository
                .findById(questionSeq)
                .orElseThrow(() -> new EntityNotFoundException(String.format("sessionQuestion entity does not exist questionSeq: %s", questionSeq)));

        sqe.changeState(req.getStateCode());

        if (req.getStateCode() != SessionQuestionStateCode.BEFORE_ANSWER) {
            String command = CommandType.QuestionStatusChange.getValue();
            String commandKey = getCommandKey();

            int isProgress = req.getStateCode() == SessionQuestionStateCode.ANSWERING ? 1 : 0;

            Map<String, Object> commandMap = new HashMap<>();

            commandMap.put("command", command);
            commandMap.put("commandKey", commandKey);

            commandMap.put("sessionId", commandKey);
            commandMap.put("surveyNo", questionSeq);
            commandMap.put("isProgress", isProgress);

            redisPubService.publish(commandMap);
        }
    }

    private String getCommandKey() {
        return RedisPubService.COMMAND_KEY + SequenceUtils.getSequence();
    }
}
