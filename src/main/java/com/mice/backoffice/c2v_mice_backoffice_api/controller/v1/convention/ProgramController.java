package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.program.*;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.program.ProgramService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/programs")
public class ProgramController {
    // Service
    private final ProgramService programService;

    @GetMapping("/{programId}")
    public C2VResponse get(@PathVariable("programId") long programId) throws Exception {
        ProgramGetResponse programGetResponse = programService.get(programId);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(programGetResponse)
                .build();
    }

    @PostMapping
    public C2VResponse create(@RequestBody ProgramCreateRequest req,
                              HttpServletResponse response) throws Exception {
        ProgramCreateResponse programCreateResponse = programService.create(req);

        response.setStatus(HttpStatus.CREATED.value());

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(programCreateResponse)
                .build();
    }

    @PutMapping("/{programId}")
    public C2VResponse update(@PathVariable("programId") long programId,
                              @Valid @RequestBody ProgramUpdateRequest req) throws Exception {
        programService.update(programId, req);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @DeleteMapping("/{programId}")
    public C2VResponse delete(@PathVariable("programId") long programId) throws Exception {
        programService.delete(programId);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @Operation(summary = "프로그램 리스트", description = "프로그램 리스트 검색 결과 제공")
    @GetMapping
    public C2VResponse findProgramForList(ProgramListRequest req) throws Exception{
        return programService.findProgramForList(req);
    }
}
