package ru.innopolis.stc27.maslakov.enterprise.project42.services.order;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;

import java.util.Set;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO findOrderById(Long id);

    OrderDTO changeStatus(Long id, OrderStatus status);

    Set<OrderDTO> getOrdersForWaiters();

    Set<OrderDTO> getOrdersByStatus(OrderStatus status);
}
