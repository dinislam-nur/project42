package ru.innopolis.stc27.maslakov.enterprise.project42.utils;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;

import java.util.stream.Collectors;

public class DTOConverter {
    public static OrderDTO convertToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getUser().getId(),
                order.getOrderTime(),
                order.getStatus(),
                order.getFoods().stream().map(Food::getId).collect(Collectors.toList()),
                order.getTable().getId(),
                order.isPayed(),
                order.getTotalSum()
        );
    }
}
