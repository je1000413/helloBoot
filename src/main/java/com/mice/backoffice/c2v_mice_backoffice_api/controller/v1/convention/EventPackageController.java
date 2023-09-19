package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.model.convention.eventpackage.*;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.eventpackage.EventPackageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/packages")
public class EventPackageController {
    // Service
    private final EventPackageService eventPackageService;

    @GetMapping
    public C2VResponse list(EventPackageQueryParam condition) throws Exception {
        EventPackageListResponse eventPackageListResponse = eventPackageService.list(condition);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(eventPackageListResponse)
                .build();
    }

    @GetMapping("/{packageId}")
    public C2VResponse get(@PathVariable("packageId") String packageId) throws Exception {
        EventPackageGetResponse eventPackageGetResponse = eventPackageService.get(packageId);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(eventPackageGetResponse)
                .build();
    }

    @PostMapping
    public C2VResponse create(@Valid @RequestBody EventPackageCreateRequest req,
                              HttpServletResponse response) throws Exception {
        EventPackageCreateResponse eventPackageCreateResponse = eventPackageService.create(req);

        response.setStatus(HttpStatus.CREATED.value());

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(eventPackageCreateResponse)
                .build();
    }

    @PatchMapping("/{packageId}")
    public C2VResponse changePackageType(@PathVariable("packageId") String packageId,
                                         @RequestBody EventPackagePatchRequest req) throws Exception {
        eventPackageService.changePackageType(packageId, req);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @PutMapping("/{packageId}")
    public C2VResponse update(@PathVariable("packageId") String packageId,
                              @RequestBody EventPackageUpdateRequest req) throws Exception {
        eventPackageService.update(packageId, req);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }

    @DeleteMapping("/{packageId}")
    public C2VResponse delete(@PathVariable("packageId") String packageId) throws Exception {
        eventPackageService.delete(packageId);

        return C2VResponse
                .builder()
                .code(Constants.ResponseCode.SUCCESS.value())
                .msg(Constants.ResponseCode.SUCCESS.toString())
                .data(null)
                .build();
    }
}
