package com.mice.backoffice.c2v_mice_backoffice_api.config;

import com.google.gson.Gson;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.CodeDto;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.CsvDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class CodeConfig {
    private final String CODES_PATH = "/codes/codes.json";
    private final Gson gson;
    private final PathConfig pathConfig;

    @Bean
    public Map<String, List<CodeDto>> init() throws IOException {
        String codes = StreamUtils.copyToString(new ClassPathResource(CODES_PATH).getInputStream(), StandardCharsets.UTF_8);
        return gson.fromJson(codes, Map.class);
    }


    @Bean
    public Map<String, List<CsvDto>> csvCodeInit() throws RuntimeException {
        Map<String, List<CsvDto>> csvData = new HashMap<>();
        csvData.put("emotion", this.CsvToMap(pathConfig.emotion, 1, 2));
        csvData.put("effect", this.CsvToMap(pathConfig.effect, 1, 2));
        csvData.put("spaceOption", this.CsvToMap(pathConfig.spaceOption, 1, 2));
        csvData.put("spaceBaseObject", this.CsvToMap(pathConfig.spaceBaseObject, 1, 2));
        csvData.put("spaceTemplate", this.CsvToMap(pathConfig.spaceTemplate, 1, 2));
        csvData.put("spaceTemplateArrangement", this.CsvToMap(pathConfig.spaceTemplateArrangement, 1, 2));
        csvData.put("localizationStringForMice", this.CsvToMap(pathConfig.localizationStringForMice, 1, 2));
        csvData.put("localizationStringForOffice", this.CsvToMap(pathConfig.localizationStringForOffice, 1, 2));
        return csvData;
    }

    public static List<CsvDto> CsvToMap(String path, int headerRowNum, int startRowNum) throws RuntimeException {

        List<CsvDto> rawData = new ArrayList<>();

        //csv파싱
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource(path).getInputStream(), StandardCharsets.UTF_8))) {


            // pattern :    "\[-*\d+(.*\d+)?, -*\d+(.*\d+)?, -*\d+(.*\d+)?]"
            // pattern :    "\[[0-9.,\s\-]+\]"
            String line;
            Integer currentRow = 0;
            while ((line = reader.readLine()) != null) {
                currentRow++;
                rawData.add(CsvDto.builder().rowNum(currentRow).rawData(line.replaceAll("\"\\[[0-9.,\\s\\-]+\\]\"", "*").split(",", -1)).build());
            }

            //Headers
            String[] colNames = rawData.get(headerRowNum).getRawData();

            //Rows
            rawData.stream().skip(startRowNum).forEach(r -> {
                Map<String, Object> row = new HashMap<>();
                IntStream.range(0, Arrays.stream(colNames).toList().size()).forEach(i -> {
                    try {
                        row.put(colNames[i], r.getRawData()[i]);
                    } catch (Exception ex) {
                        //System.out.println(ex.getMessage());
                    }
                });
                r.setRow(row);
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rawData;
    }


}
