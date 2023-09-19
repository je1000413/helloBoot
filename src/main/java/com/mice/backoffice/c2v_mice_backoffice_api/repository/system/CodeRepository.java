package com.mice.backoffice.c2v_mice_backoffice_api.repository.system;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.system.CodeEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.exception.RaisErrorException;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public interface CodeRepository extends JpaRepository<CodeEntity, CodeEntity.CodeEntityPK>, CodeCustomRepository {

    @ExceptionHandler(RaisErrorException.class)
    @Procedure(procedureName = "sp_sys_code_info_select")
    List<CodeEntity> findAllByCdTyp(@Param("in_cd_typ") String cdTyp, @Param("in_cd") Integer cd);

    List<CodeEntity> findAllByCdTyp(String cdTyp);

    CodeEntity findByCdTypAndCd(String cdTyp, Integer cd);
}
