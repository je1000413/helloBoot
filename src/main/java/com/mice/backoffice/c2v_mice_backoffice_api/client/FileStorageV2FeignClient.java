package com.mice.backoffice.c2v_mice_backoffice_api.client;

import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.fileStorage.FileStorageCustomerRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.fileStorage.FileStorageUploadRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "fileStorageV2", url = "${fileStorageV2.domain}")
public interface FileStorageV2FeignClient {

    //customer 등록
    @PostMapping("/api/v1/customers")
    C2VResponse createCustomer(@RequestBody FileStorageCustomerRequest request);

    //directory 생성
    @PostMapping("/api/v1/directories")
    C2VResponse createDirectory(@RequestHeader("CustomerAuthKey") String CustomerAuthKey, @RequestBody Map<String, String> request);

    //directory 조회
    @GetMapping("/api/v1/directory-info")
    C2VResponse getDirectory(@RequestHeader("CustomerAuthKey") String CustomerAuthKey, @RequestParam("directory-path") String request);

    //file 업로드
    @PutMapping("/api/v1/file-url")
    C2VResponse uploadFile(@RequestHeader("CustomerAuthKey") String CustomerAuthKey, @RequestBody FileStorageUploadRequest request);

    //file 업로드 완료
    @PostMapping("/api/v1/file-url/complete")
    C2VResponse completeUploadFile(@RequestHeader("CustomerAuthKey") String CustomerAuthKey, @RequestBody Map<String, String> request);

    //file 다운로드 url
    @GetMapping("/api/v1/file-url")
    C2VResponse downloadFileUrl(@RequestHeader("CustomerAuthKey") String CustomerAuthKey, @RequestParam Map<String, String> request);

    //file 다운로드 redirect url
    @GetMapping("/api/v1/file-url/redirect")
    C2VResponse downloadFileUrlRedirect(@RequestHeader("CustomerAuthKey") String CustomerAuthKey, @RequestBody Map<String, String> request);
}
