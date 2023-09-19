package com.mice.backoffice.c2v_mice_backoffice_api.common.support.attributeconverter;

import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports;
import com.mice.backoffice.c2v_mice_backoffice_api.common.support.SessionSupports.*;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
class SessionTypeConverter implements AttributeConverter<SessionType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SessionType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public SessionType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(SessionType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class SessionEndTypeConverter implements AttributeConverter<SessionEndType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SessionEndType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public SessionEndType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(SessionEndType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class SessionStateCodeConverter implements AttributeConverter<SessionStateCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SessionStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public SessionStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(SessionStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class SessionDisplayStateCodeConverter implements AttributeConverter<SessionDisplayStateCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SessionDisplayStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public SessionDisplayStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(SessionDisplayStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class SessionOnlineCodeConverter implements AttributeConverter<SessionOnlineCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SessionOnlineCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public SessionOnlineCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(SessionOnlineCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class StaffTypeConverter implements AttributeConverter<StaffType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StaffType attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public StaffType convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(StaffType.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class TicketOnlineCodeConverter implements AttributeConverter<TicketOnlineCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TicketOnlineCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public TicketOnlineCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(TicketOnlineCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class SessionQuestionStateCodeConverter implements AttributeConverter<SessionQuestionStateCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SessionQuestionStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public SessionQuestionStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(SessionQuestionStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

@Converter(autoApply = true)
class TicketStateCodeConverter implements AttributeConverter<TicketStateCode, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TicketStateCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getType();
    }

    @Override
    public TicketStateCode convertToEntityAttribute(Integer type) {
        if (type == null) {
            return null;
        }

        return Stream.of(TicketStateCode.values())
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}