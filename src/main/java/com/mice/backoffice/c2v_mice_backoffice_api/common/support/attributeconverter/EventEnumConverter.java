package com.mice.backoffice.c2v_mice_backoffice_api.common.support.attributeconverter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

import static com.mice.backoffice.c2v_mice_backoffice_api.common.support.EventSupports.*;

@Converter(autoApply = true)
class PriceTypeConverter implements AttributeConverter<PriceType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PriceType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public PriceType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(PriceType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class HostCodeConverter implements AttributeConverter<HostCode, Integer>{
    @Override
    public Integer convertToDatabaseColumn(HostCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public HostCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(HostCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class TicketEndTypeConverter implements AttributeConverter<TicketEndType, Integer>{
    @Override
    public Integer convertToDatabaseColumn(TicketEndType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public TicketEndType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(TicketEndType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class EventStateCodeConverter implements AttributeConverter<EventStateCode, Integer>{
    @Override
    public Integer convertToDatabaseColumn(EventStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public EventStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(EventStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class EventDisplayStateCodeConverter implements AttributeConverter<EventDisplayStateCode, Integer>{
    @Override
    public Integer convertToDatabaseColumn(EventDisplayStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public EventDisplayStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(EventDisplayStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class EventSellStateCodeConverter implements AttributeConverter<EventSellStateCode, Integer>{
    @Override
    public Integer convertToDatabaseColumn(EventSellStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public EventSellStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(EventSellStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class EventSpaceStateCodeConverter implements AttributeConverter<EventSpaceStateCode, Integer>{
    @Override
    public Integer convertToDatabaseColumn(EventSpaceStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public EventSpaceStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(EventSpaceStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}