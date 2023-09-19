package com.mice.backoffice.c2v_mice_backoffice_api.model.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class FileStorageRequest {
    private String serviceName;
    private String path;
    private String file;

    public String getUrl( String domain, String uri){
        String url = new StringBuilder()
                .append(domain)
                .append(uri)
                .append( "?service-name=")
                .append(this.serviceName)
                .append("&path=")
                .append(this.path)
                .append("&file=")
                .append(this.file)
                .toString();
        return url;
    }
}
