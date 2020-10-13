package ru.innopolis.stc27.maslakov.enterprise.project42.entities.order;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OrderStatusAttributeConverter implements AttributeConverter<OrderStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrderStatus status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case COLLECT:
                return 1;
            case USER_CONFIRMED:
                return 2;
            case STAFF_CONFIRMED:
                return 3;
            case PREPARING:
                return 4;
            case DONE:
                return 5;
            default:
                throw new IllegalArgumentException("Нет соответствующего значения для статуса заказа: " + status);
        }
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer dbInteger) {
        if (dbInteger == null) {
            return null;
        }
        switch (dbInteger) {
            case 1:
                return OrderStatus.COLLECT;
            case 2:
                return OrderStatus.USER_CONFIRMED;
            case 3:
                return OrderStatus.STAFF_CONFIRMED;
            case 4:
                return OrderStatus.PREPARING;
            case 5:
                return OrderStatus.DONE;
            default:
                throw new IllegalArgumentException("Нет соответствующего статуса заказа для значения: " + dbInteger);
        }
    }
}
