package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.session;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.session.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackageRepository extends JpaRepository<PackageEntity, String> {
    @Query(value = "select nextval('package')", nativeQuery = true)
    String findPackageId();
}
