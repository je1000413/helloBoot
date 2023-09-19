package com.mice.backoffice.c2v_mice_backoffice_api.service.convention.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.mice.backoffice.c2v_mice_backoffice_api.client.CloudStorageFeignClient;
import com.mice.backoffice.c2v_mice_backoffice_api.client.FileStorageV2FeignClient;
import com.mice.backoffice.c2v_mice_backoffice_api.common.Constants;
import com.mice.backoffice.c2v_mice_backoffice_api.config.EncodeConfig;
import com.mice.backoffice.c2v_mice_backoffice_api.entity.convention.FileStorageKeyEntity;
import com.mice.backoffice.c2v_mice_backoffice_api.model.common.FileUploadRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.fileStorage.FileStorageCustomerRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.model.fileStorage.FileStorageUploadRequest;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.FileStorageKeyRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.convention.event.EventRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.convention.FileHandlerService;
import feign.Feign;
import feign.FeignException;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FileHandlerServiceImpl implements FileHandlerService {

    @Autowired
    EventRepository eventRepository;
    CloudStorageFeignClient cloudStorageFeignClient;
    @Autowired
    FileStorageV2FeignClient fileStorageV2FeignClient;
    @Autowired
    FileStorageKeyRepository fileStorageKeyRepository;

    @Value("${fileStorageV2.managementId}")
    private String managementId;

    @Override
    public C2VResponse uploadFileEditor(List<MultipartFile> fileList) throws NoSuchAlgorithmException, IOException, C2VException {

        String customerAuthKey = getCustomerAuthKey("editor");
        Map<String, Object> map = new HashMap<>();

        LocalDateTime now = LocalDateTime.now();
        String seq = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String directoryPath = getDirectoryPath(customerAuthKey, new StringBuilder().append("/").append(seq).toString());

        List<Map<String,String>> mapList = new ArrayList<>();
        for(int i =0; i< fileList.size(); i++){
            Map<String, String> urlMap = new HashMap<>();
            MultipartFile newFile = getFile(fileList.get(i));
            urlMap.put("fileName", newFile.getName());
            urlMap.put("url",uploadFile(customerAuthKey, makeFileSorageUploadRequest(directoryPath,  newFile), newFile));
            mapList.add(urlMap);
        }
        map.put("file", mapList);

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("성공").data(map).build();
    }

    @Override
    public C2VResponse uploadFile(FileUploadRequest fileUploadRequest) throws NoSuchAlgorithmException, IOException, C2VException {

        Map<String, Object> map = new HashMap<>();

        String eventId = null;
        if(fileUploadRequest.getEventId() == null){
            eventId = String.valueOf(eventRepository.findEventId());
        }else if(String.valueOf(1).equals(fileUploadRequest.getEventId())) {
            eventId = "event";
        }else eventId  = fileUploadRequest.getEventId();

        String customerAuthKey = getCustomerAuthKey(eventId);
        String directoryPath = getDirectoryPath(customerAuthKey, fileUploadRequest.getPath(eventId));

        List<Map<String, String>> mapList = new ArrayList<>();

        for(int i =0; i< fileUploadRequest.getFiles().length; i++){
            Map<String, String> urlMap = new HashMap<>();

            MultipartFile newFile = getFile(fileUploadRequest.getFiles()[i]);
            urlMap.put("fileName", newFile.getName());
            urlMap.put("url", uploadFile(customerAuthKey, makeFileSorageUploadRequest(directoryPath, newFile), newFile));
            mapList.add(urlMap);
        }

        map.put("eventId", eventId);
        map.put("file", mapList);

        return C2VResponse.builder().code(Constants.ResponseCode.SUCCESS.value()).msg("성공").data(map).build();
    }

    public MultipartFile getFile(MultipartFile originalFile) throws IOException {
        String seq = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String originalFileName =originalFile.getOriginalFilename(); // 기존 파일 이름을 가져옵니다.
        String newFileName = seq + "_" + originalFileName; // 새로운 파일 이름을 지정합니다.

        MultipartFile newFile = new MockMultipartFile(newFileName, originalFileName, originalFile.getContentType(), originalFile.getInputStream());
        return newFile;
    }

    public String getCustomerAuthKey(String customerKey) throws IOException {
        String customerAuthKey = fileStorageKeyRepository.findByCustomerKey(customerKey);

        if(customerAuthKey == null || customerAuthKey.isEmpty() || customerAuthKey.isBlank()){

            FileStorageCustomerRequest fileStorageCustomerRequest = FileStorageCustomerRequest.builder()
                    .managementId(Integer.parseInt(managementId))
                    .customerName(customerKey)
                    .rootPath(new StringBuilder().append(customerKey).append("/").toString())
                    .build();

            Map<String, Object> data = (Map<String, Object>) fileStorageV2FeignClient.createCustomer(fileStorageCustomerRequest).getData();

            customerAuthKey = (String) data.get("customerAuthKey");

            fileStorageKeyRepository.save(FileStorageKeyEntity.dataBuilder(customerKey, customerAuthKey).build());
        }
        return customerAuthKey;
    }


    public String getDirectoryPath(String customerAuthKey, String directoryPath) throws C2VException {
        try {
            if(checkDirectoryPath(customerAuthKey, directoryPath) == false) {
                Map<String, String> map = new HashMap<>();
                map.put("directoryPath", directoryPath);
                fileStorageV2FeignClient.createDirectory(customerAuthKey, map);
            }
        }catch (FeignException fe){
            throw new C2VException(ResponseCode.BAD_REQUEST,"Create DirectoryPath Fail / " + fe.getMessage());
        }
        return directoryPath;
    }

    public Boolean checkDirectoryPath(String customerAuthKey, String directoryPath) throws C2VException {
        try {
            C2VResponse c2v = fileStorageV2FeignClient.getDirectory(customerAuthKey, directoryPath);
            if(c2v.getCode() == 200) return true;
            else return false;
        }catch (FeignException fe){
            return false;
        }
    }

    public String uploadFile(String customerAuthKey, FileStorageUploadRequest req, MultipartFile file) throws IOException, C2VException {
        try {
            Map<String, String> data = (Map<String, String>) fileStorageV2FeignClient.uploadFile(customerAuthKey, req).getData();

            uploadCloud(req.getMd5(), data.get("signed-url"), toBinary(file));

            completeUploadFile(customerAuthKey,data.get("key"));

            return downloadFileUrl(customerAuthKey, req.getFileName(), req.getDirectoryPath());
        }catch (FeignException fe){
            throw new C2VException(ResponseCode.BAD_REQUEST,"uploadFile Fail / " + fe.getMessage());
        }
    }

    public void completeUploadFile(String customerAuthKey, String key) throws IOException, C2VException {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("key", key);

            fileStorageV2FeignClient.completeUploadFile(customerAuthKey, map);
        }catch (FeignException fe){
            throw new C2VException(ResponseCode.BAD_REQUEST,"complete upload file Fail / " + fe.getMessage());
        }
    }

    public void uploadCloud(String md5, String url, byte[] file) throws IOException, C2VException {
        //googleStorage CALL
        RequestInterceptor interceptor = template -> {
            template.header("Content-MD5", md5);
        };
        cloudStorageFeignClient = Feign.builder()
                .encoder(new EncodeConfig())
                .requestInterceptor(interceptor)
                .target(CloudStorageFeignClient.class, url);

        try {
            cloudStorageFeignClient.upload(file);
        }catch (FeignException fe){
            throw new C2VException(ResponseCode.BAD_REQUEST,"upload file On Cloud Fail / " + fe.getMessage());
        }
    }

    public String downloadFileUrl(String customerAuthKey, String fileName, String directoryPath) throws IOException, C2VException {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("file-name", fileName);
            map.put("directory-path", directoryPath);
            map.put("max-url-expire", String.valueOf(1));

            String data = (String) fileStorageV2FeignClient.downloadFileUrl(customerAuthKey, map).getData();

            return data;
        }catch (FeignException fe){
            throw new C2VException(ResponseCode.BAD_REQUEST,"download file Fail / " + fe.getMessage());
        }
    }

    public FileStorageUploadRequest makeFileSorageUploadRequest(String directoryPath, MultipartFile file) throws IOException, NoSuchAlgorithmException {

        return FileStorageUploadRequest.builder()
                .directoryPath(directoryPath)
                .fileName(file.getName())
                .size(file.getSize())
                .md5(makeMD5(file))
                .expireDays(0)
                .accessType(1)
                .build();
    }

    public String makeMD5(MultipartFile file) throws IOException, NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[4096];
        int bytesRead;
        try (InputStream inputStream = file.getInputStream()) {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
        }
        byte[] digest = md.digest();

        String base64EncodedMD5 = Base64.getEncoder().encodeToString(digest);

        return base64EncodedMD5;
    }

    public static byte[] toBinary(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        byte[] bytes = StreamUtils.copyToByteArray(inputStream);
        inputStream.close();
        return bytes;
    }
}