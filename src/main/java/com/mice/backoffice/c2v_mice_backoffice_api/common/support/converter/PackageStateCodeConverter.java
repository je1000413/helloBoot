package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageStateCode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PackageStateCodeConverter implements Converter<String, PackageStateCode> {

    @Override
    public PackageStateCode convert(String source) {
        PackageStateCode result = null;

        for (PackageStateCode value : PackageStateCode.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
