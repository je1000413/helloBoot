package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.NoticeBoardSearchArticleType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NoticeBoardSearchArticleTypeConverter implements Converter<String, NoticeBoardSearchArticleType> {

    @Override
    public NoticeBoardSearchArticleType convert(String source) {
        Supports.NoticeBoardSearchArticleType result = null;

        for (NoticeBoardSearchArticleType value : NoticeBoardSearchArticleType.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
