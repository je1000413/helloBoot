package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.SessionStateCode;
import org.springframework.core.convert.converter.Converter;

public class SessionStateCodeConverter implements Converter<String, SessionStateCode> {

    @Override
    public SessionStateCode convert(String source) {
        SessionStateCode result = null;

        for (SessionStateCode value : SessionStateCode.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
