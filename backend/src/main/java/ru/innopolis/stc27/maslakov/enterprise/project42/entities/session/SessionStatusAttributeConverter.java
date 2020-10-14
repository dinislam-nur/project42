package ru.innopolis.stc27.maslakov.enterprise.project42.entities.session;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SessionStatusAttributeConverter implements AttributeConverter<SessionStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(SessionStatus sessionStatus) {
        if (sessionStatus == null) {
            return null;
        }
        switch (sessionStatus) {
            case OPENED:
                return 1;
            case CLOSED:
                return 2;
            default:
                throw new IllegalArgumentException("Нет соответствующего значения для статуса сессии: " + sessionStatus);
        }
    }

    @Override
    public SessionStatus convertToEntityAttribute(Integer dbInteger) {
        if (dbInteger == null) {
            return null;
        }
        switch (dbInteger) {
            case 1:
                return SessionStatus.OPENED;
            case 2:
                return SessionStatus.CLOSED;
            default:
                throw new IllegalArgumentException("Нет соответствующего статуса сессии для значения: " + dbInteger);
        }
    }
}
