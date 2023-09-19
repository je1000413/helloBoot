package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageSearchDateType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PackageSearchDateTypeConverter implements Converter<String, PackageSearchDateType> {

    @Override
    public PackageSearchDateType convert(String source) {
        PackageSearchDateType result = null;

        for (PackageSearchDateType value : PackageSearchDateType.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
