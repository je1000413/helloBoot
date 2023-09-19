package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.DomainEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.NoticeBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoardEntity, Integer> {
}
