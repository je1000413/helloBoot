package com.mice.backoffice.c2v_mice_backoffice_api.common.support.attributeconverter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports.AdminLogActionCode;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.AdminSupports.AdminLogMappingType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
class AdminLogMappingTypeConverter implements AttributeConverter<AdminLogMappingType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AdminLogMappingType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public AdminLogMappingType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(AdminLogMappingType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class AdminLogActionCodeConverter implements AttributeConverter<AdminLogActionCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AdminLogActionCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public AdminLogActionCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(AdminLogActionCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}