package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge.LoungeCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge.LoungeListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.lounge.LoungeUpdateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.LoungeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lounge")
public class LoungeController {
    @Autowired
    private LoungeService loungeService;

    @Operation(summary = "라운지 리스트 조회", description = "라운지 리스트 조회")
    @PostMapping("/list")
    public C2VResponse list( @RequestBody LoungeListRequest request) throws C2VException {
        return loungeService.list(request);
    }
    @Operation(summary = "라운지 조회", description = "라운지 조회")
    @GetMapping("/{loungeNo}")
    public C2VResponse get(@PathVariable(name = "loungeNo") int loungeNo) throws C2VException {
        return loungeService.get(loungeNo);
    }
    @Operation(summary = "라운지 생성", description = "라운지 생성")
    @PostMapping()
    public C2VResponse create(@Valid @RequestBody LoungeCreateRequest request) throws C2VException, NoSuchAlgorithmException, IOException {
        return loungeService.create(request);
    }
    @Operation(summary = "라운지 수정", description = "라운지 수정")
    @PutMapping("/{loungeNo}")
    public C2VResponse update(@PathVariable(name = "loungeNo") int loungeNo,@Valid @RequestBody LoungeUpdateRequest request) throws C2VException, NoSuchAlgorithmException, IOException {
        return loungeService.update(loungeNo, request);
    }
    @Operation(summary = "라운지 삭제", description = "라운지 삭제")
    @DeleteMapping("/{loungeNo}")
    public C2VResponse delete(@PathVariable int loungeNo) throws C2VException {
        return loungeService.delete(loungeNo);
    }
}
