package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageSearchStaffType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PackageSearchStaffTypeConverter implements Converter<String, PackageSearchStaffType> {
    @Override
    public PackageSearchStaffType convert(String source) {
        PackageSearchStaffType result = null;

        for (PackageSearchStaffType value : PackageSearchStaffType.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
