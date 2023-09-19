package com.mice.backoffice.c2v_mice_backoffice_api.service.system.impl;

import com.com2verse.platform.exception.C2VException;
import com.com2verse.platform.object.C2VResponse;
import com.com2verse.platform.object.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mice.backoffice.c2v_mice_backoffice_api.common.CommonUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.common.RequestUtils;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.CodeDto;
import com.mice.backoffice.c2v_mice_backoffice_api.repository.system.CodeRepository;
import com.mice.backoffice.c2v_mice_backoffice_api.service.system.CodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;
    private final Map<String, List<CodeDto>> codeConfigMap;

    private final CommonUtils commonUtils;

    @Override
    public C2VResponse findAll(Map<String, Object> params) throws C2VException {
        return getCommonResponse(codeRepository.findAll(params), log, params);
    }

    @Override
    public C2VResponse findAllById(Map<String, Object> params) throws C2VException {
        return getCommonResponse(codeRepository.findAllById(params), log, params);
    }

    @Override
    public C2VResponse add(Map<String, Object> params) throws C2VException {
        return getCommonResponse(codeRepository.add(params), log, params);
    }

    @Override
    public C2VResponse modify(Map<String, Object> params) throws C2VException {
        return getCommonResponse(codeRepository.modify(params), log, params);
    }

    @Override
    public C2VResponse delete(Map<String, Object> params) throws C2VException {
        return getCommonResponse(codeRepository.delete(params), log, params);
    }

    @Override
    public C2VResponse findAllByCdTyp(String cdTyp) throws C2VException {
        //return getCommonResponse(codeRepository.findAllByCdTyp(cdTyp), log, null);
        return getCommonResponse(codeConfigMap.entrySet().stream().filter(k -> k.getKey().equals(cdTyp)).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)), log, null);
    }

    @Override
    public C2VResponse findByCdTypAndCd(String cdTyp, Integer cd) throws C2VException {
        //return getCommonResponse(codeRepository.findByCdTypAndCd(cdTyp, cd), log, null);
        return getCommonResponse(codeConfigMap.entrySet().stream().filter(k -> k.getKey().equals(cdTyp) && k.getValue().equals(cd)).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)), log, null);
    }

    @Override
    public C2VResponse getSpaceTemplate() throws C2VException {
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(commonUtils.getSpaceTemplate()).build();
    }

    @Override
    public C2VResponse getSpaceTemplate(String cdTyp) throws C2VException {
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(commonUtils.getSpaceTemplate().get(cdTyp)).build();
    }


    @Override
    public  C2VResponse jsonTOdb() throws C2VException, IOException {

        List<Map<String, Object>> codes = new ArrayList<>();
        Map<String, Object> param = new LinkedHashMap<>();
        Map<String, Object> description = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        /* Jackson */
        String codeJson =StreamUtils.copyToString(new ClassPathResource("/codes/codes.json").getInputStream(), StandardCharsets.UTF_8);
        Map<String, List<Object>> codeMap = objectMapper.readValue(codeJson, Map.class);
        //System.out.println(codeMap);

        codeMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue))
                .forEach((k,v) -> {
                    v.forEach(c -> {
                        //var code = objectMapper.convertValue(c, new TypeReference<CodeDto>(){});
                        var code = objectMapper.convertValue(c, CodeDto.class);

                        try {
                            description.clear();
                            description.put("prop", code.getProp());
                            //description.put("description", objectMapper.writeValueAsString(code.getDetailValue()));

                            description.put("description", code.getDetailValue());

                            param.clear();
        //                    param.put("in_cur_admin_id", RequestUtils.getCurAdminId());
        //                    param.put("in_code_category", k);
        //                    param.put("in_code_category_name", k);
        //                    param.put("in_code_name", objectMapper.writeValueAsString(code.getName()));
        //                    param.put("in_code", code.getValue());
        //                    param.put("in_code_description", objectMapper.writeValueAsString(description));
        //                    param.put("in_code_no", code.getSort());
                            //codeRepository.add(param);

                            Map<String, Object> newMap = Map.of(
                                    "code_category", k,
                                    "code_category_name", k,
                                    "code_name", code.getName(),
                                    "code", code.getValue(),
                                    "code_description", description,
                                    "code_no", code.getSort(),
                                    "use_yn", code.getUseYn()
                            );

                            codes.add(newMap);


                            System.out.println(newMap);

                        }
                        catch(Exception ex) {
                            log.error(String.format("%s : %s"), ex.getMessage(), code.toString());
                        }
                    });
                });

        param.clear();
        param.put("in_json", objectMapper.writeValueAsString(codes));
        return C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(codeRepository.CreateViewPlatformCode(param)).build();
    }

    /**
     * 메소드 정의
     * **/
    C2VResponse getCommonResponse(List<Object> data, Logger log, Map<String, Object> params) {

        C2VResponse c2VResponse = C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();

        return c2VResponse;
    }

    C2VResponse getCommonResponse(Object data, Logger log, Map<String, Object> params) {

        C2VResponse c2VResponse = C2VResponse.builder().code(ResponseCode.OK.code()).msg(ResponseCode.OK.msg()).data(data).build();

        return c2VResponse;
    }
}
