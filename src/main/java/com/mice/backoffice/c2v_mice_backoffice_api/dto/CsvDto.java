package com.mice.backoffice.c2v_mice_backoffice_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CsvDto {

    private int rowNum;

    @JsonIgnore
    private String[] rawData;

    private Map<String, Object> row;

    @Builder
    public CsvDto(int rowNum, String[] rawData, HashMap<String, Object> row) {
        this.rowNum = rowNum;
        this.rawData = rawData;
        this.row = row;
    }

    @Override
    public String toString() {
        return String.format("row : %s, rawData : %s", getRowNum(), String.join(",", getRawData()));
    }
}
