package com.mice.backoffice.c2v_mice_backoffice_api.model.common;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class FileRequest {
    private String serviceName;
    private String path;
    private MultipartFile file;
    public String getUrl(String domain, String uri){
        String url = new StringBuilder()
                .append(domain)
                .append(uri)
                .append( "?service-name=")
                .append(serviceName)
                .append("&path=/")
                .append(path)
                .append("&file=")
                .append(file.getName())
                .toString();
        return url;
    }
}
