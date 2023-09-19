package com.mice.backoffice.c2v_mice_backoffice_api.common.support.attributeconverter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports.ProgramType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.ProgramSupports.SwitchType;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
class ProgramTypeConverter implements AttributeConverter<ProgramType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProgramType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public ProgramType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(ProgramType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class SwitchTypeConverter implements AttributeConverter<SwitchType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SwitchType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public SwitchType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(SwitchType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}