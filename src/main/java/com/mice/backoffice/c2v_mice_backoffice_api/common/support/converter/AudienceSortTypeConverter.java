package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ChatSupports.AudienceSortType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AudienceSortTypeConverter implements Converter<String, AudienceSortType> {

    @Override
    public AudienceSortType convert(String source) {
        AudienceSortType result = null;

        for (AudienceSortType value : AudienceSortType.values())
            if (value.getType() == Integer.parseInt(source))
                result = value;

        return result;
    }
}