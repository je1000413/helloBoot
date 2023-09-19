package com.mice.backoffice.c2v_mice_backoffice_api.client;

import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;


public interface CloudStorageFeignClient {
    @RequestLine("PUT")
    void upload(@RequestBody byte[] file);

}
