package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Builder;
import lombok.Value;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Value
public class OrderDTO {

    Long id;

    Long userId;

    Timestamp timestamp;

    OrderStatus status;

    List<Long> foodsId;

    UUID TableId;

    Boolean payed;

    /**
     * Сумма заказа
     */
    Double total;
}
