package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.order.OrderService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/api/orders")
    public Set<OrderDTO> orders() {
        return orderService.getOrdersByStatus(OrderStatus.USER_CONFIRMED);
    }

    @PostMapping(value = "/api/create_order")
    @ResponseBody
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }
}
