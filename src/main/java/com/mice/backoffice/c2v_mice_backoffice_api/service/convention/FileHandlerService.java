package com.mice.backoffice.c2v_mice_backoffice_api.service.convention;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.mice.backoffice.c2v_mice_backoffice_api.model.common.FileUploadRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface FileHandlerService {
    C2VResponse uploadFileEditor(List<MultipartFile> fileList) throws NoSuchAlgorithmException, IOException, C2VException;
    C2VResponse uploadFile(FileUploadRequest fileUploadRequest) throws NoSuchAlgorithmException, IOException, C2VException;

}
