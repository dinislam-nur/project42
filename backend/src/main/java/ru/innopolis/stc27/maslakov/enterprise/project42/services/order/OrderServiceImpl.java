package ru.innopolis.stc27.maslakov.enterprise.project42.services.order;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.FoodRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.OrderRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

import java.sql.Timestamp;
import java.time.Instant;
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
    public OrderDTO createNewOrder(OrderDTO orderDTO) {
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
        orderDTO.getFoodsId().forEach(id -> {
            foods.forEach(entity -> {
                if (entity.getId().equals(id)) {
                    listFoods.add(entity);
                }
            });
        });
        val currentOrder = Order.builder()
                .id(null)
                .user(user)
                .orderTime(new Timestamp(Instant.now().getEpochSecond()))
                .table(table)
                .foods(listFoods)
                .status(OrderStatus.USER_CONFIRMED)
                .payed(true)
                .totalSum(listFoods.stream().mapToDouble(Food::getPrice).sum())
                .build();

        val saved = orderRepository.save(currentOrder);
        return DTOConverter.convertToDTO(saved);
    }

    @Override
    public OrderDTO findOrderById(Long id) {
        return DTOConverter.convertToDTO(
                orderRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalStateException("В БД не существует заказа с id #" + id)));
    }

    @Override
    @Transactional
    public void updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Заказа с id = %d е существует", id)));
        order.setStatus(orderDTO.getStatus());
        order.setPayed(orderDTO.getPayed());
        order.setTotalSum(orderDTO.getTotal());
        if (id.equals(orderDTO.getId())) {
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Неправильный запрос");
        }
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Collection<OrderDTO> getOrdersForWaiters() {
        return orderRepository
                .findOrdersByStatusBetween(OrderStatus.PREPARING, OrderStatus.DONE)
                .stream()
                .map(DTOConverter::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<OrderDTO> getOrders(OrderStatus status, User id) {
        if (status != null) {
            return orderRepository
                    .findByStatus(status)
                    .stream()
                    .map(DTOConverter::convertToDTO)
                    .collect(Collectors.toSet());
        }
        else if (id != null) {
            List<OrderDTO> listId = new ArrayList<>();
            Iterable<Order> all = orderRepository.findByUser(id);
            for (Order order : all) {
                if (order.getUser().equals(id)) {
                    listId.add(DTOConverter.convertToDTO(order));
                }
            }
            listId.sort(Comparator.comparing(OrderDTO::getTimestamp).reversed());
            return listId;
        } else {
            List<OrderDTO> list = new ArrayList<>();
            Iterable<Order> all = orderRepository.findAll();
            for (Order order : all) {
                list.add(DTOConverter.convertToDTO(order));
            }
            list.sort(Comparator.comparing(OrderDTO::getTimestamp).reversed());
            return list;
        }
    }
}
