package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports;
import org.springframework.core.convert.converter.Converter;

public class EventSpaceStateCodeConverter implements Converter<String, EventSupports.EventSpaceStateCode> {
    @Override
    public EventSupports.EventSpaceStateCode convert(String source) {
        EventSupports.EventSpaceStateCode result = null;

        for (EventSupports.EventSpaceStateCode value : EventSupports.EventSpaceStateCode.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
