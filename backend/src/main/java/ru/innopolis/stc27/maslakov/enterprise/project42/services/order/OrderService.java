package ru.innopolis.stc27.maslakov.enterprise.project42.services.order;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;

import java.util.Collection;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO findOrderById(Long id);

    OrderDTO changeStatus(Long id, OrderStatus status);

    Collection<OrderDTO> deleteOrder(Long id);

    Collection<OrderDTO> getOrdersForWaiters();

    Collection<OrderDTO> getOrdersByStatus(OrderStatus status);

    Collection<OrderDTO> getListOrders();
}
