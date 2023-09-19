package com.mice.backoffice.c2v_mice_backoffice_api.controller.v1.convention;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.common.FileUploadRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.FileHandlerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Transactional
@RestController
@AllArgsConstructor
@RequestMapping("/api/files")
public class FileHandlerController {
    private final FileHandlerService fileHandlerService;

    @Operation(summary = "파일 업로드(에디터용)", description = "파일 업로드(에디터용)")
    @PostMapping(value = "/upload/list/editor",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public C2VResponse uploadFileEditor(@RequestPart("fileList") List<MultipartFile> fileList) throws NoSuchAlgorithmException, IOException, C2VException {
        return fileHandlerService.uploadFileEditor(fileList);
    }
    @Operation(summary = "파일 업로드", description = "파일 업로드")
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public C2VResponse uploadFile(FileUploadRequest fileUploadRequest) throws NoSuchAlgorithmException, IOException, C2VException {
        return fileHandlerService.uploadFile(fileUploadRequest);
    }
}