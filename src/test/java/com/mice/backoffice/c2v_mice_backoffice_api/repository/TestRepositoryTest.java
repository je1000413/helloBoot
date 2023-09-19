package com.mice.backoffice.c2v_mice_backoffice_api.repository;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LanguageEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LoungeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.LoungeEntity.LoungeId;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.LoungeRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.mybatis.TestRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LanguageService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.kafka.common.protocol.types.Field;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
@ActiveProfiles("local")
class TestRepositoryTest {

    @Test
    void test() {

    }
}
