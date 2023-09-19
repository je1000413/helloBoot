package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner.BannerDisplayCreateRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner.BannerDisplayListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.banner.BannerListRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.banner.BannerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/banner")
public class BannerController {
    // Service
    private final BannerService bannerService;

    @PostMapping("/list")
    public C2VResponse list(@RequestBody BannerListRequest request) throws C2VException {
        return bannerService.list(request);
    }

    @PostMapping("/display/list")
    public C2VResponse displayList(@RequestBody BannerDisplayListRequest request) throws C2VException {
        return bannerService.displayList(request);
    }

    @GetMapping("/display/{displaySeq}")
    public C2VResponse getDisplay(@PathVariable long displaySeq) throws C2VException {
        return bannerService.getDisplay(displaySeq);
    }

    @PostMapping("/display")
    public C2VResponse createDisplay(@Valid @RequestBody BannerDisplayCreateRequest request) throws C2VException {
        return bannerService.createDisplay(request);
    }

    @PutMapping("/display/{displaySeq}")
    public C2VResponse updateDisplay(@PathVariable long displaySeq, @Valid @RequestBody BannerDisplayCreateRequest request) throws C2VException {
        return bannerService.updateDisplay(displaySeq, request);
    }

    @DeleteMapping("/display/{displaySeq}")
    public C2VResponse deleteDisplay(@PathVariable long displaySeq) throws C2VException {
        return bannerService.deleteDisplay(displaySeq);
    }
}
