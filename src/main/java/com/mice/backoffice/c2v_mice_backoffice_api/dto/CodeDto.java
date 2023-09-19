package com.mice.backoffice.c2v_mice_backoffice_api.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.LanguageType;
import lombok.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class CodeDto {
    private Map<LanguageType, String> name;
    private String value;
    private String prop;
    private int sort;
    private char useYn = 'y';

    @JsonProperty("detail_value")
    private Map<String, Object> detailValue;

    @Builder
    public CodeDto(Map<LanguageType, String> name, String value, String prop, int sort, char useYn, Map<String, Object> detailValue) {
        this.name = name;
        this.value = value;
        this.prop = prop;
        this.sort = sort;
        this.useYn = useYn;
        this.detailValue = detailValue;
    }

    @Override
    public String toString(){
        return String.format("{ \"name\" : \"%s\",  \"value\" : \"%s\",  \"prop\" : \"%s\", \"sort\" : \"%s\", \"useYn\" : \"%s\", \"detailValue\" : \"%s\"", getName(), getValue(), getProp(), getSort(), getUseYn(), getDetailValue());
    }
}
