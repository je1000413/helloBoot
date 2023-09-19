package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardSearchRollingType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NoticeBoardSearchRollingTypeConverter implements Converter<String, NoticeBoardSearchRollingType> {

    @Override
    public NoticeBoardSearchRollingType convert(String source) {
        NoticeBoardSearchRollingType result = null;

        for (NoticeBoardSearchRollingType value : NoticeBoardSearchRollingType.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
