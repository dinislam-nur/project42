package ru.innopolis.stc27.maslakov.enterprise.project42.services.order;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.FoodRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.OrderRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.OrderDTOConverter;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.ServicesUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TableRepository tableRepository;
    private final FoodRepository foodRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        ServicesUtils.checkOrderDTO(orderDTO);
        val userId = orderDTO.getUserId();
        val user = userRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Пользователя с id #" + userId + " не найдено в базе"));

        val tableId = orderDTO.getTableId();
        val table = tableRepository
                .findById(tableId)
                .orElseThrow(() -> new IllegalStateException(
                        "Стола с id #" + tableId + " не найдено в базе"));

        final List<Food> foods = new ArrayList<>();
        foodRepository
                .findAllById(orderDTO.getFoodsId())
                .forEach(foods::add);
        val currentOrder = Order.builder()
                .id(null)
                .user(user)
                .table(table)
                .foods(foods)
                .status(OrderStatus.USER_CONFIRMED)
                .payed(orderDTO.getPayed())
                .totalSum(orderDTO.getTotal())
                .build();

        val saved = orderRepository.save(currentOrder);
        return OrderDTOConverter.convert(saved);
    }

    @Override
    public OrderDTO findOrderById(Long id) {
        return OrderDTOConverter.convert(
                orderRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalStateException("В БД не существует заказа с id #" + id)));
    }

    @Override
    public OrderDTO changeStatus(Long id, OrderStatus status) {
        val order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("В БД не существует заказа с id #" + id));
        order.setStatus(status);
        final Order updated = orderRepository.save(order);
        return OrderDTOConverter.convert(updated);
    }

    @Override
    public Collection<OrderDTO> deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return getListOrders();
    }

    @Override
    public Collection<OrderDTO> getOrdersForWaiters() {
        return orderRepository
                .findOrdersByStatusBetween(OrderStatus.PREPARING, OrderStatus.DONE)
                .stream()
                .map(OrderDTOConverter::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<OrderDTO> getOrdersByStatus(OrderStatus status) {
        return orderRepository
                .findByStatus(status)
                .stream()
                .map(OrderDTOConverter::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<OrderDTO> getListOrders() {
        List<OrderDTO> list = new ArrayList<>();
        Iterable<Order> all = orderRepository.findAll();
        for (Order order:all) {
            list.add(OrderDTOConverter.convert(order));
        }
        list.sort(Comparator.comparing(OrderDTO::getTimestamp).reversed());
        return list;
    }
}
