package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionQuestionStateCode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SessionQuestionStateCodeConverter implements Converter<String, SessionQuestionStateCode> {

    @Override
    public SessionQuestionStateCode convert(String source) {
        SessionQuestionStateCode result = null;

        for (SessionQuestionStateCode value : SessionQuestionStateCode.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
