package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.order.OrderService;

import java.util.Collection;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/orders/{status}")
    public Collection<OrderDTO> orders(@PathVariable("status") OrderStatus status) {
        return orderService.getOrdersByStatus(status);
    }

    @GetMapping(value = "/orders")
    public Collection<OrderDTO> listOrders() {
        return orderService.getListOrders();
    }

    @GetMapping(value = "/orders/{id}")
    public OrderDTO orderById(@PathVariable("id") Long id) {
        return orderService.findOrderById(id);
    }

    @GetMapping(value = "/orders/for_waiters")
    public Collection<OrderDTO> getOrdersForWaiters() {
        return orderService.getOrdersForWaiters();
    }

    @PostMapping(value = "/orders")
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PostMapping(value = "/orders/{id}/{status}")
    public OrderDTO changeStatus(@PathVariable("id") Long id, @PathVariable("status") OrderStatus status) {
        return orderService.changeStatus(id, status);
    }

    @DeleteMapping(value = "/orders/delete/{id}")
    public Collection<OrderDTO> deleteOrder(@PathVariable("id") Long id) {
        return orderService.deleteOrder(id);
    }
}
