package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.BannerDisplayEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BannerRepository extends JpaRepository<BannerEntity, Long> {

    @Query("select be " +
            "from BannerEntity be " +
            "where be.bannerId = :bannerId " +
            "and be.stateCode <> 3")
    BannerEntity findByBannerId(long bannerId);

}
