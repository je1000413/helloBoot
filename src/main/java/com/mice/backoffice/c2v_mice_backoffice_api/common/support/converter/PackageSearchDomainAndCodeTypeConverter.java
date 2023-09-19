package com.mice.backoffice.c2v_mice_backoffice_api.common.support.converter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.PackageSearchDomainAndCodeType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PackageSearchDomainAndCodeTypeConverter implements Converter<String, PackageSearchDomainAndCodeType> {

    @Override
    public PackageSearchDomainAndCodeType convert(String source) {
        PackageSearchDomainAndCodeType result = null;

        for (PackageSearchDomainAndCodeType value : PackageSearchDomainAndCodeType.values()) {
            if (value.getType() == Integer.parseInt(source))
                result = value;
        }

        return result;
    }
}
