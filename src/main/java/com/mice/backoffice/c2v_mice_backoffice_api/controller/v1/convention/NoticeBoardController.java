package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.*;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.NoticeBoardService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/board/notice")
public class NoticeBoardController {
    // Service
    private final NoticeBoardService noticeBoardService;

    @GetMapping
    public C2VResponse list(NoticeBoardQueryParam cond) throws Exception {
        NoticeBoardListResponse noticeBoardListResponse = noticeBoardService.list(cond);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(noticeBoardListResponse)
                .build();
    }

    @GetMapping("/{boardSeq}")
    public C2VResponse get(@PathVariable("boardSeq") int boardSeq) throws Exception {
        NoticeBoardGetResponse noticeBoardGetResponse = noticeBoardService.get(boardSeq);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(noticeBoardGetResponse)
                .build();
    }

    @PostMapping
    public C2VResponse create(@RequestBody NoticeBoardCreateRequest req,
                              HttpServletResponse response) throws Exception {
        NoticeBoardCreateResponse noticeBoardCreateResponse = noticeBoardService.create(req);

        response.setStatus(HttpStatus.CREATED.value());

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(noticeBoardCreateResponse)
                .build();
    }

    @PutMapping("/{boardSeq}")
    public C2VResponse put(@RequestBody NoticeBoardUpdateRequest req,
                           @PathVariable("boardSeq") int boardSeq) throws Exception {
        noticeBoardService.update(req, boardSeq);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @DeleteMapping("/{boardSeq}")
    public C2VResponse delete(@PathVariable("boardSeq") int boardSeq) throws Exception {
        noticeBoardService.delete(boardSeq);

        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public C2VResponse options(HttpServletResponse response) throws Exception {
        return C2VResponse
                .builder()
                .code(ResponseCode.SUCCESS.value())
                .msg(ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }
}
