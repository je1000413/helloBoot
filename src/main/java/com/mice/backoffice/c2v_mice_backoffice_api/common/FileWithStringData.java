package com.mice.backoffice.c2v_mice_backoffice_api.common;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

public class FileWithStringData {
    private MultipartFile multipartFile;
    private String string;

    public FileWithStringData(MultipartFile multipartFile, String string) {
        this.multipartFile = multipartFile;
        this.string = string;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(Object file) {
        if (file instanceof MultipartFile) {
            this.multipartFile = (MultipartFile) file;
        } else {
            throw new IllegalArgumentException("Invalid data type: expected MultipartFile but received " + file.getClass().getSimpleName());
        }
    }

    public String getString() {
        return string;
    }

    public void setString(Object data) {
        if (data instanceof String) {
            this.string = (String) data;
        } else {
            throw new IllegalArgumentException("Invalid data type: expected String but received " + data.getClass().getSimpleName());
        }
    }

    public static Map<Supports.LanguageType, String>  setString(Map<Supports.LanguageType, FileWithStringData> originalMap){
        Map<Supports.LanguageType, String> result = new HashMap<>();
        for (Map.Entry<Supports.LanguageType, FileWithStringData> entry : originalMap.entrySet()) {
            Supports.LanguageType languageType = entry.getKey();
            FileWithStringData fileWithStringData = entry.getValue();
            String stringData = fileWithStringData.getString();
            result.put(languageType, stringData);
        }
        return result;
    }

    public static Map<Supports.LanguageType, MultipartFile> setMultipartfile(Map<Supports.LanguageType, FileWithStringData> originalMap){
        Map<Supports.LanguageType, MultipartFile> result = new HashMap<>();
        for (Map.Entry<Supports.LanguageType, FileWithStringData> entry : originalMap.entrySet()) {
            Supports.LanguageType languageType = entry.getKey();
            FileWithStringData fileWithStringData = entry.getValue();
            MultipartFile stringData = fileWithStringData.getMultipartFile();
            result.put(languageType, stringData);
        }
        return result;
    }
}