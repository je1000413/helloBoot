package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageSearchNameAndCodeType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PackageSearchNameAndCodeTypeConverter implements Converter<String, PackageSearchNameAndCodeType> {

    @Override
    public PackageSearchNameAndCodeType convert(String source) {
        PackageSearchNameAndCodeType result = null;

        for (PackageSearchNameAndCodeType value : PackageSearchNameAndCodeType.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
