package com.mice.backoffice.c2v_mice_backoffice_api.service.admin.impl;

import com.mice.backoffice.c2v_mice_backoffice_api.dto.AdminLogCreateDto;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminLogEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.admin.AdminLogRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.admin.AdminLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AdminLogServiceImpl implements AdminLogService {
    // Service

    // Repository
    private final AdminLogRepository adminLogRepository;


    @Override
    @Transactional
    public void create(AdminLogCreateDto dto) {
        AdminLogEntity ale = AdminLogEntity
                .dataBuilder(dto)
                .build();

        adminLogRepository.save(ale);
    }
}
