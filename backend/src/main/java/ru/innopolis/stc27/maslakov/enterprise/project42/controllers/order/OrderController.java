package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.order.OrderService;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @GetMapping(value = "/orders")
    public Collection<OrderDTO> orders(
            @RequestParam(value = "status", required = false) OrderStatus status) {
        return orderService.getOrders(status);
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
        return orderService.createNewOrder(orderDTO);
    }

    @PutMapping(value = "/orders/{id}")
    public void updateOrder(
            @PathVariable("id") Long id,
            @RequestBody @NonNull OrderDTO orderDTO) {
        orderService.updateOrder(id, orderDTO);
    }

    @DeleteMapping(value = "/orders/delete/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
    }
}
