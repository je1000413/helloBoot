package com.mice.backoffice.c2v_mice_backoffice_api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestUtils {

    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            return servletRequestAttributes.getRequest();
        } else {
            return null;
        }
    }

    public static Long getCurAdminId(){
        try{
            return  Long.valueOf((String) getHttpServletRequest().getAttribute("adminId"));
        }catch (Exception e){
            return 0L;
        }
    }

    public static Integer getCurMenuSeq(){
        try{
            return Integer.valueOf(getHttpServletRequest().getHeader("cur-menu-seq"));
        }catch (Exception e){
            return 0;
        }
    }

    public static String getCurLangCd(){
        String curLangCd = null;
        try{
            HttpServletRequest httpServletRequest = getHttpServletRequest();
            if(httpServletRequest == null){
                return "ko_kr";
            }
            if(httpServletRequest.getHeader("cur-lang-cd") == null){
                return "ko_kr";
            }
            String header = httpServletRequest.getHeader("cur-lang-cd");
            if(header.isEmpty()){
                return "ko_kr";
            }else{
                return header;
            }
        }catch (Exception e){
            return "ko_kr";
        }
    }

    public static Map<String, Object> getSqlRequest(Object obj){
        ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        LinkedHashMap<String, Object> map = objectMapper.convertValue(obj, LinkedHashMap.class);
        Map<String, Object> inParams = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entrySet : map.entrySet()) {
            inParams.put("in_" + entrySet.getKey(), entrySet.getValue());
        }
        return inParams;
    }
}
