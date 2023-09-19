package com.mice.backoffice.c2v_mice_backoffice_api.common.support.attributeconverter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.Supports.*;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
class SurveyCodeConverter implements AttributeConverter<SurveyCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SurveyCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public SurveyCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(SurveyCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class SurveyStateCodeConverter implements AttributeConverter<SurveyStateCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SurveyStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public SurveyStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(SurveyStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class SurveyMappingTypeConverter implements AttributeConverter<SurveyMappingType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SurveyMappingType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public SurveyMappingType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(SurveyMappingType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class LanguageTypeConverter implements AttributeConverter<Supports.LanguageType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Supports.LanguageType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public Supports.LanguageType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(Supports.LanguageType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class DisplayCodeConverter implements AttributeConverter<DisplayCode, Integer>{
    @Override
    public Integer convertToDatabaseColumn(DisplayCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public DisplayCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(DisplayCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class DisplayTypeConverter implements AttributeConverter<DisplayType, Integer>{
    @Override
    public Integer convertToDatabaseColumn(DisplayType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public DisplayType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(DisplayType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class ScreenStateCodeConverter implements AttributeConverter<ScreenStateCode, Integer>{
    @Override
    public Integer convertToDatabaseColumn(ScreenStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public ScreenStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(ScreenStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class ScreenMappingTypeConverter implements AttributeConverter<ScreenMappingType, Integer>{
    @Override
    public Integer convertToDatabaseColumn(ScreenMappingType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public ScreenMappingType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(ScreenMappingType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class ScreenDisplayStateCodeConverter implements AttributeConverter<ScreenDisplayStateCode, Integer>{
    @Override
    public Integer convertToDatabaseColumn(ScreenDisplayStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public ScreenDisplayStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(ScreenDisplayStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class PackageTypeConverter implements AttributeConverter<PackageType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PackageType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public PackageType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(PackageType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class PackageAuthorityCodeConverter implements AttributeConverter<PackageAuthorityCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PackageAuthorityCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public PackageAuthorityCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(PackageAuthorityCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class PackageStateCodeConverter implements AttributeConverter<PackageStateCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PackageStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public PackageStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(PackageStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class NoticeBoardArticleTypeConverter implements AttributeConverter<NoticeBoardArticleType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(NoticeBoardArticleType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public NoticeBoardArticleType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(NoticeBoardArticleType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class LoungeStateCodeConverter implements AttributeConverter<LoungeStateCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(LoungeStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public LoungeStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(LoungeStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class LoungeSearchNameAndCodeTypeConverter implements AttributeConverter<LoungeSearchNameAndCodeType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(LoungeSearchNameAndCodeType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public LoungeSearchNameAndCodeType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(LoungeSearchNameAndCodeType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class LoungeSearchDomainAndCodeTypeConverter implements AttributeConverter<LoungeSearchDomainAndCodeType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(LoungeSearchDomainAndCodeType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public LoungeSearchDomainAndCodeType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(LoungeSearchDomainAndCodeType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class BannerSearchDateTypeConverter implements AttributeConverter<BannerSearchDateType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BannerSearchDateType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public BannerSearchDateType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(BannerSearchDateType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class BannerCodeConverter implements AttributeConverter<BannerCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BannerCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public BannerCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(BannerCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class BannerStateCodeConverter implements AttributeConverter<BannerStateCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BannerStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public BannerStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(BannerStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class BannerSearchNameAndCodeTypeConverter implements AttributeConverter<BannerSearchNameAndCodeType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BannerSearchNameAndCodeType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public BannerSearchNameAndCodeType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(BannerSearchNameAndCodeType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class BannerDisplaySearchDateTypeConverter implements AttributeConverter<BannerDisplaySearchDateType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BannerDisplaySearchDateType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public BannerDisplaySearchDateType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(BannerDisplaySearchDateType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class BannerDisplayStateCodeConverter implements AttributeConverter<BannerDisplayStateCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BannerDisplayStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public BannerDisplayStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(BannerDisplayStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class BannerDisplaySearchNameAndCodeTypeConverter implements AttributeConverter<BannerDisplaySearchNameAndCodeType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BannerDisplaySearchNameAndCodeType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public BannerDisplaySearchNameAndCodeType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(BannerDisplaySearchNameAndCodeType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class LoungeTypeConverter implements AttributeConverter<LoungeType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(LoungeType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public LoungeType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(LoungeType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class OperatorAdminStaffTypeConverter implements AttributeConverter<OperatorAdminStaffType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OperatorAdminStaffType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public OperatorAdminStaffType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(OperatorAdminStaffType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class OperatorAdminUseYnConverter implements AttributeConverter<OperatorAdminUseYn, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OperatorAdminUseYn attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public OperatorAdminUseYn convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(OperatorAdminUseYn.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class PartnerAdminStaffTypeConverter implements AttributeConverter<PartnerAdminStaffType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PartnerAdminStaffType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public PartnerAdminStaffType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(PartnerAdminStaffType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}