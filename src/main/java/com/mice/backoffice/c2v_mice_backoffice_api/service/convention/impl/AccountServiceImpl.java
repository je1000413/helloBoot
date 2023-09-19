package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.AudienceDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.AudienceListParam;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.account.AccountEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.AccountRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.querydsl.JpaQueryAccountRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    // Service

    // Repository
    private final JpaQueryAccountRepository jpaQueryAccountRepository;

    @Override
    public Page<AudienceDto> findAllByAccountId(AudienceListParam param, Pageable pageable) {
        return jpaQueryAccountRepository.findAudienceWithPaging(param, pageable);
    }
}
