package com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.program;

import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.program.ProgramListEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.exception.RaisErrorException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

public interface ProgramCustomRepository {

    @ExceptionHandler(RaisErrorException.class)
    List<ProgramListEntity> findProgramForList(Map<String, Object> params);
}
