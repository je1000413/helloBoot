package com.mice.backoffice.c2v_mice_backoffice_api.repository.admin;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.admin.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    AdminEntity findByAccountName(String accountName);

    AdminEntity findByAdminId(Long adminId);

    void save(Long adminId);
}
