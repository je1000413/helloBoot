package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardSearchDateType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NoticeBoardSearchDateTypeConverter implements Converter<String, NoticeBoardSearchDateType> {

    @Override
    public NoticeBoardSearchDateType convert(String source) {
        NoticeBoardSearchDateType result = null;

        for (NoticeBoardSearchDateType value : NoticeBoardSearchDateType.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
