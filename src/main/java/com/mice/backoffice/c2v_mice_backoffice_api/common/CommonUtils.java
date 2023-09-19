package com.mice.backoffice.c2v_mice_backoffice_api.common;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.dto.CsvDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CommonUtils {

    @Value("${tokens.system}")
    private String systemToken;

    private final Map<String, List<CsvDto>> csvDtoList;

    private CsvDto findFirstCsvDto(List<CsvDto> src, String key, Object val) {
        return src.stream().filter(s -> s.getRow().get(key).equals(val)).findFirst().get();
    }

    public Map<String, Object> getSpaceTemplate() {

        List<CsvDto> spaceOption = csvDtoList.get("spaceOption").stream().skip(2).filter(f -> f.getRow().get("ServiceType").equals("MICE")).toList();
        List<CsvDto> spaceTemplate = csvDtoList.get("spaceTemplate").stream().skip(2).filter(c -> c.getRow().get("SpaceType").equals("Mice")).toList();
        List<CsvDto> spaceTemplateArrangement = csvDtoList.get("spaceTemplateArrangement").stream().skip(2).toList();
        List<CsvDto> spaceBaseObject = csvDtoList.get("spaceBaseObject").stream().skip(2).toList();

        //옵션타입
        Map<String, List<Map<String, Object>>> options = Map.of(
                "type1", Arrays.asList(
                        Map.of("optionKey", "useYn_Y", "optionName", "사용", "optionValue", "Y"),
                        Map.of("optionKey", "useYn_N", "optionName", "미사용", "optionValue", "N")
                ),
                "type2", Arrays.asList(
                        Map.of("optionKey", "image", "optionName", "이미지", "optionValue", "20003"),
                        Map.of("optionKey", "video", "optionName", "동영상", "optionValue", "20002"),
                        Map.of("optionKey", "useYn_N", "optionName", "미사용", "optionValue", "N")
                ),
                "type3", Arrays.asList(
                        Map.of("optionKey", "image", "optionName", "이미지", "optionValue", "20005"),
                        Map.of("optionKey", "video", "optionName", "동영상", "optionValue", "20002"),
                        Map.of("optionKey", "useYn_N", "optionName", "미사용", "optionValue", "N")
                ),
                "type4", Arrays.asList(
                        Map.of("optionKey", "image", "optionName", "이미지", "optionValue", "20005"),
                        Map.of("optionKey", "useYn_N", "optionName", "미사용", "optionValue", "N")
                ),
                "session_type", Arrays.asList(
                        Map.of("name", Map.of("ko_kr", "선택", "en_us", "select"), "value", 1, "sort", 1),
                        Map.of("name", Map.of("ko_kr", "실시간 live", "en_us", "live streaming"), "value", 2, "sort", 2),
                        Map.of("name", Map.of("ko_kr", "vod 스트리밍", "en_us", "vod streaming"), "value", 3, "sort", 3),
                        Map.of("name", Map.of("ko_kr", "vod 플레이", "en_us", "vod play"), "value", 4, "sort", 4),
                        Map.of("name", Map.of("ko_kr", "youtube 플레이", "en_us", "youtube play"), "value", 7, "sort", 5)
                ),
                "special_meetup_session_type", Arrays.asList(
                        Map.of("name", Map.of("ko_kr", "화상제공형", "en_us", "select"), "value", 5, "sort", 1, "max_member_count", 200),
                        Map.of("name", Map.of("ko_kr", "보이스채팅형", "en_us", "live streaming"), "value", 6, "sort", 2, "max_member_count", 200)
                )
        );

        //디스플레이코드
        var displayOptions =  Map.of(
                "LeafletScreen", Map.of("display_name", Supports.DisplayCode.LEAFLET.toString(), "display_code", Supports.DisplayCode.LEAFLET.getType(), "options", options.get("type1")),
                "Kiosk", Map.of("display_name", Supports.DisplayCode.KIOSK.toString(),"display_code", Supports.DisplayCode.KIOSK.getType(), "options", options.get("type1")),
                "KioskSpecial", Map.of("display_name", Supports.DisplayCode.SPECIAL_KIOSK.toString(),"display_code", Supports.DisplayCode.SPECIAL_KIOSK.getType(), "options", options.get("type1")),
                "DynamicScreen", Map.of("display_name", Supports.DisplayCode.AD_SCREEN.toString(),"display_code", Supports.DisplayCode.AD_SCREEN.getType(), "options", options.get("type2")),
                "Banner", Map.of("display_name", Supports.DisplayCode.BANNER.toString(),"display_code", Supports.DisplayCode.BANNER.getType(), "options", options.get("type2")),
                "LectureScreen", Map.of("display_name", Supports.DisplayCode.LECTURE_SCREEN.toString(),"display_code", Supports.DisplayCode.LECTURE_SCREEN.getType(), "options", options.get("type1")),
                "DynamicScreenForPromotion", Map.of("display_name", Supports.DisplayCode.PROMOTION_SCREEN.toString(),"display_code", Supports.DisplayCode.PROMOTION_SCREEN.getType(), "options", options.get("type3")),
                "DynamicScreenForBanner", Map.of("display_name", Supports.DisplayCode.WALL_BANNER.toString(),"display_code", Supports.DisplayCode.WALL_BANNER.getType(), "options", options.get("type1")),
                "DynamicScreenForHall", Map.of("display_name", Supports.DisplayCode.AD_SCREEN.toString(),"display_code", Supports.DisplayCode.AD_SCREEN.getType(), "options", options.get("type3"))
        );

        Map<String, Object> codeMap = new LinkedHashMap<>();
        spaceOption.stream().forEach(s_option -> {
            try {
                var filterSpaceTemplate = spaceTemplate.stream().filter(s_template -> s_template.getRow().get("SpaceCode").equals(s_option.getRow().get("SpaceCode"))).toList();
                codeMap.put(s_option.getRow().get("SpaceCode").toString(),
                        filterSpaceTemplate.stream().map(s_template -> Map.of(
                                "name", Map.of("ko_kr", s_template.getRow().get("#템플릿 이름"), "en_us", s_template.getRow().get("Title")),
                                "value", s_template.getRow().get("ID"),
                                "items", spaceTemplateArrangement
                                        .stream()
                                        .filter(s_arragement -> s_arragement.getRow().get("TemplateID").equals(s_template.getRow().get("ID")))
                                        .filter(s_arragement -> {
                                            var baseObject = findFirstCsvDto(spaceBaseObject, "ID", s_arragement.getRow().get("BaseObjectID"));
                                            //return displayOptions.containsKey(baseObject.getRow().get("ObjectType"));
                                            return !baseObject.getRow().get("ObjectType").equals("Object");
                                        })
                                        .map(s_arragement -> {

                                            var baseObject = findFirstCsvDto(spaceBaseObject, "ID", s_arragement.getRow().get("BaseObjectID"));
                                            Map<String, Object> item = new HashMap<>();
                                            item.put("space_object_id"  , s_arragement.getRow().get("SpaceObjectID"));
                                            item.put("ObjectType"       , baseObject.getRow().get("ObjectType"));
                                            //item.put("base_objcet_id"   , baseObject.getRow().get("ID"));
                                            //item.put("base_objcet_name" , baseObject.getRow().get("Name"));

                                            /*
                                            MiceLobby - DynamicScreen - MICE_Screen_AD_04
                                            MiceLounge - 1235 - DynamicScreen - MICE_Screen_AD_01
                                            MiceConferenceHall - 1237 - DynamicScreen - MICE_Screen_AD_02, 1312 DynamicScreen - MICE_Screen_AD_05
                                            1239 - DynamicScreen - MICE_Screen_AD_03,
                                            1312 - DynamicScreen - MICE_Screen_AD_05,

                                            컨퍼런스 - 1236 - LectureScreen - MICE_Screen_Lecture_01
                                            밋업 - 1238 - LectureScreen - MICE_Screen_Lecture_02
                                            밋업 - 1239 - DynamicScreen - MICE_Screen_AD_03
                                            */

                                            //MiceLounge
                                            if(baseObject.getRow().get("ObjectType").equals("LeafletScreen") && baseObject.getRow().get("Name").toString().indexOf("MICE_LeafletStand_Normal") > -1) {
                                                item.putAll(displayOptions.get("LeafletScreen"));
                                            } else if(baseObject.getRow().get("ObjectType").equals("Kiosk") && baseObject.getRow().get("Name").toString().indexOf("MICE_Kiosk_Normal") > -1) {
                                                item.putAll(displayOptions.get("Kiosk"));
                                            } else if(baseObject.getRow().get("ObjectType").equals("Kiosk") && baseObject.getRow().get("Name").toString().indexOf("MICE_Kiosk_Spacial") > -1) {
                                                item.putAll(displayOptions.get("KioskSpecial"));
                                            } else if(baseObject.getRow().get("ObjectType").equals("DynamicScreen") && baseObject.getRow().get("Name").toString().indexOf("MICE_Screen_AD_01") > -1) {
                                                //item.putAll(displayOptions.get("DynamicScreen"));
                                                if (s_template.getRow().get("SpaceCode").toString().equals("MiceConferenceHall")) {
                                                    item.putAll(displayOptions.get("DynamicScreenForHall"));
                                                } else {
                                                    item.putAll(displayOptions.get("DynamicScreen"));
                                                }
                                            } else if(baseObject.getRow().get("ObjectType").equals("DynamicScreen") && baseObject.getRow().get("Name").toString().indexOf("MICE_Screen_AD_02") > -1) {
                                                item.putAll(displayOptions.get("DynamicScreenForPromotion"));
                                                //item.put("session_type", options.get("session_type"));
                                            } else if(baseObject.getRow().get("ObjectType").equals("DynamicScreen") && baseObject.getRow().get("Name").toString().indexOf("MICE_Screen_AD_05") > -1) {
                                                item.putAll(displayOptions.get("DynamicScreenForBanner"));
                                                //item.put("session_type", options.get("session_type"));
                                            } else if(baseObject.getRow().get("ObjectType").equals("LectureScreen") && baseObject.getRow().get("Name").toString().indexOf("MICE_Screen_Lecture_01") > -1) {
                                                item.putAll(displayOptions.get("LectureScreen"));
                                                //item.put("session_type", options.get("session_type"));
                                            } else if(baseObject.getRow().get("ObjectType").equals("LectureScreen") && baseObject.getRow().get("Name").toString().indexOf("MICE_Screen_Lecture_02") > -1) {
                                                //스페셜밋업
                                                item.putAll(displayOptions.get("LectureScreen"));
                                                //item.put("session_type", options.get("special_meetup_session_type"));
                                            } else if(baseObject.getRow().get("ObjectType").equals("DynamicScreen") && baseObject.getRow().get("Name").toString().indexOf("MICE_Screen_AD_03") > -1) {
                                                //스페셜밋업
                                                item.putAll(displayOptions.get("DynamicScreenForPromotion"));
                                                //item.put("session_type", options.get("special_meetup_session_type"));
                                            }

                                            return  item;
                                        })
                                        .toList(),
                                "session_type", s_template.getRow().get("SpaceCode").toString().equals("MiceConferenceHall") ? options.get("session_type") : s_template.getRow().get("SpaceCode").toString().equals("MiceMeetUp") ? options.get("special_meetup_session_type") : Arrays.asList()
                        )).toList()
                );
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        return codeMap;
    }
    public String getSystemToken() {
        return "Bearer " + systemToken;
    }

}
