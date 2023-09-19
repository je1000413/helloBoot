package com.mice.backoffice.c2v_mice_backoffice_api.repository.admin;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminLogRepository extends JpaRepository<AdminLogEntity, Long> {
}
