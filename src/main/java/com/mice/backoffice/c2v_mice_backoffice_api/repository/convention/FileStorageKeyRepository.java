package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.FileStorageKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileStorageKeyRepository extends JpaRepository<FileStorageKeyEntity, String> {

    @Query("select e.customerAuthKey " +
            "from FileStorageKeyEntity e " +
            "where e.customerKey = :customerKey ")
    String findByCustomerKey(String customerKey);

}
