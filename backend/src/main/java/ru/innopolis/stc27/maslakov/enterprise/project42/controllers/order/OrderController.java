package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.order;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.PrimaryOrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.order.OrderService;

import java.net.URI;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/orders")
    public Collection<OrderDTO> orders(
            @RequestParam(value = "status", required = false) OrderStatus status,
            @RequestParam(value = "id", required = false) Long userId) {
        return orderService.getOrders(status, userId);
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
    public ResponseEntity<String> createOrder(
            @RequestBody @NonNull PrimaryOrderDTO orderDTO) {
        val id = orderService.createNewOrder(orderDTO);
        return ResponseEntity
                .created(URI.create("/orders/" + id))
                .build();
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

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String accessDenied(AccessDeniedException exception) {
        return exception.getMessage();
    }
}
