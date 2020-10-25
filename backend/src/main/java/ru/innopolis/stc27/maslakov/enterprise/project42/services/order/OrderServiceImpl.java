package ru.innopolis.stc27.maslakov.enterprise.project42.services.order;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.PrimaryOrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.FoodRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.OrderRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TableRepository tableRepository;
    private final FoodRepository foodRepository;

    @Override
    public Long createNewOrder(PrimaryOrderDTO orderDTO) {
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
        final List<Food> listFoods = new ArrayList<>();
        orderDTO.getFoodsId().forEach(
                id -> foods
                        .stream()
                        .filter(food -> id.equals(food.getId()))
                        .forEachOrdered(listFoods::add)
        );
        val currentOrder = Order.builder()
                .user(user)
                .table(table)
                .foods(listFoods)
                .build();

        return orderRepository.save(currentOrder).getId();
    }

    @Override
    public OrderDTO findOrderById(Long id) {
        return DTOConverter.convertToDTO(
                orderRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalStateException("В БД не существует заказа с id #" + id)));
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_WAITER', 'ROLE_CHIEF', 'ROLE_ADMIN')")
    public void updateOrder(Long id, OrderDTO orderDTO) {
        if (id.equals(orderDTO.getId())) {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new IllegalStateException(String.format("Заказа с id = %d не существует", id)));
            order.setStatus(orderDTO.getStatus());
            order.setPayed(orderDTO.getPayed());
            order.setTotalSum(orderDTO.getTotal());
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Неправильный запрос");
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_WAITER', 'ROLE_ADMIN')")
    public Collection<OrderDTO> getOrdersForWaiters() {
        return orderRepository
                .findOrdersByStatusBetween(OrderStatus.PREPARING, OrderStatus.DONE)
                .stream()
                .map(DTOConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    @PreAuthorize("hasPermission(#userId, 'order', 'get') || hasAnyRole('ROLE_CHIEF', 'ROLE_WAITER', 'ROLE_ADMIN')")
    public Page<OrderDTO> getOrders(OrderStatus status, Long userId, Integer page, Integer size) {
        val sort = Sort.by(Sort.Direction.DESC, "orderTime");
        val pageRequest = PageRequest.of(page, size, sort);
        Page<Order> orders;
        if (status != null) {
            orders = orderRepository
                    .findByStatus(status, pageRequest);
        } else if (userId != null) {
            orders = orderRepository
                    .findByUserId(userId, pageRequest);
        } else {
            orders = orderRepository
                    .findAll(pageRequest);
        }
        return orders.map(DTOConverter::convertToDTO);
    }
}
