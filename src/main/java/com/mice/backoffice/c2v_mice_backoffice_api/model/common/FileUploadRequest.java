package com.mice.backoffice.c2v_mice_backoffice_api.model.common;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class FileUploadRequest {

    private String eventId;
    private int functionType;
    private int displayCode;
    private String index;
    private MultipartFile[] files;

    public String getPath(String eventId){
        String seq = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String path = new StringBuilder()
                .append("/")
//                .append(eventId)
//                .append("/")
                .append(Supports.FunctionType.fromValue(this.functionType))
                .append("/")
                .append(Supports.DisplayCode.fromValue(this.displayCode))
                .append("/")
                .append(seq)
                .toString();
        return path;
    }

    public String getUrl(String eventId, MultipartFile file, String domain, String uri, String serviceName){
        String fileNm = "file=" + file.getName();
        String url = new StringBuilder()
                .append(domain)
                .append(uri)
                .append("?service-name=")
                .append(serviceName)
                .append("&path=/")
                .append(getPath(eventId))
                .append("&")
                .append(fileNm)
                .toString();
        return url;
    }

}
