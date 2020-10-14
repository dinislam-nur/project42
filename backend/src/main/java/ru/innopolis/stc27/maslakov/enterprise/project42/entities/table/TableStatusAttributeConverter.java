package ru.innopolis.stc27.maslakov.enterprise.project42.entities.table;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TableStatusAttributeConverter implements AttributeConverter<TableStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TableStatus status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case RESERVED:
                return 1;
            case NOT_RESERVED:
                return 2;
            default:
                throw new IllegalArgumentException("Нет соответствующего значения для статуса: " + status);
        }
    }

    @Override
    public TableStatus convertToEntityAttribute(Integer dbInteger) {
        if (dbInteger == null) {
            return null;
        }
        switch (dbInteger) {
            case 1:
                return TableStatus.RESERVED;
            case 2:
                return TableStatus.NOT_RESERVED;
            default:
                throw new IllegalArgumentException("Нет соответствующего статуса для значения: " + dbInteger);
        }
    }
}
