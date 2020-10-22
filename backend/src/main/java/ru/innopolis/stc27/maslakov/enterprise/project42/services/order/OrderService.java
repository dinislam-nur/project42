package ru.innopolis.stc27.maslakov.enterprise.project42.services.order;

import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;

import java.util.Collection;

public interface OrderService {

    OrderDTO createNewOrder(OrderDTO orderDTO);

    OrderDTO findOrderById(Long id);

    void updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    Collection<OrderDTO> getOrdersForWaiters();

    Collection<OrderDTO> getOrders(OrderStatus status, Long id);
}
