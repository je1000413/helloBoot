package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.BannerDisplayEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BannerDisplayRepository extends JpaRepository<BannerDisplayEntity, Long> {
    @Query("select bde " +
            "from BannerDisplayEntity bde " +
            "where bde.bannerId = :bannerId ")
    BannerDisplayEntity findByBannerId(long bannerId);

    @Query("select bde " +
            "from BannerDisplayEntity bde " +
            "where bde.displaySeq = :displaySeq ")
    BannerDisplayEntity findByDisplaySeq(long displaySeq);
}
