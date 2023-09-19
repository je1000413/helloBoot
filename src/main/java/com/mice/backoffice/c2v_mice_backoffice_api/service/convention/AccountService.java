package com.mice.backoffice.c2v_mice_backoffice_api.service.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.AudienceDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.chat.AudienceListParam;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.account.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    Page<AudienceDto> findAllByAccountId(AudienceListParam param, Pageable pageable);
}
