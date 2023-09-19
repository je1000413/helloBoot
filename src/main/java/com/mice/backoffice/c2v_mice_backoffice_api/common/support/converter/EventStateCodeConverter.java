package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import org.springframework.core.convert.converter.Converter;

public class EventStateCodeConverter implements Converter<String, EventSupports.EventStateCode> {
    @Override
    public EventSupports.EventStateCode convert(String source) {
        EventSupports.EventStateCode result = null;

        for (EventSupports.EventStateCode value : EventSupports.EventStateCode.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
