package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardSearchTitleAndCreatorType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NoticeBoardSearchTitleAndCreatorTypeConverter implements Converter<String, NoticeBoardSearchTitleAndCreatorType> {

    @Override
    public NoticeBoardSearchTitleAndCreatorType convert(String source) {
        NoticeBoardSearchTitleAndCreatorType result = null;

        for (NoticeBoardSearchTitleAndCreatorType value : NoticeBoardSearchTitleAndCreatorType.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
