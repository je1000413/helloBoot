package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionQuestionListOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SessionQuestionListOrderConverter implements Converter<String, SessionQuestionListOrder> {

    @Override
    public SessionQuestionListOrder convert(String source) {
        SessionQuestionListOrder result = null;

        for (SessionQuestionListOrder value : SessionQuestionListOrder.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
