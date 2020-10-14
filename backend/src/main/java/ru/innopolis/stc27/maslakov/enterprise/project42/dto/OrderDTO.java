package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Value;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;

import java.sql.Timestamp;
import java.util.List;

@Value
public class OrderDTO {

    Long id;

    UserDTO user;

    Timestamp timestamp;

    OrderStatus status;

    List<FoodsDTO> foods;

    /**
     * Сумма заказа
     */
    Double total;
}
