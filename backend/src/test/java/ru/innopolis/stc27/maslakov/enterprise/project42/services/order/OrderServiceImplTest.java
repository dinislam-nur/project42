package ru.innopolis.stc27.maslakov.enterprise.project42.services.order;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.FoodRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.OrderRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.TableRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.repository.api.UserRepository;
import ru.innopolis.stc27.maslakov.enterprise.project42.utils.DTOConverter;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    private static final Timestamp TIMESTAMP = Timestamp.valueOf("2020-10-15 00:00:00.000000");

    private OrderService orderService;
    private OrderRepository orderRepository;
    private OrderDTO answer;
    private List<Long> foodIdList;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        val userRepository = Mockito.mock(UserRepository.class);
        val tableRepository = Mockito.mock(TableRepository.class);
        val foodRepository = Mockito.mock(FoodRepository.class);
        orderService = new OrderServiceImpl(
                orderRepository,
                userRepository,
                tableRepository,
                foodRepository);

        final User user = new User(1L, "user", "password", Role.ROLE_GUEST);
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        final Table table = new Table(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"), 1, TableStatus.NOT_RESERVED);
        Mockito.when(tableRepository.findById(table.getId()))
                .thenReturn(Optional.of(table));

        final List<Food> foods = new ArrayList<Food>() {{
            add(new Food(1L, "compot", 1.0, "test.ru", FoodCategory.DRINKS));
            add(new Food(2L, "borsh", 2.0, "test.ru", FoodCategory.HOT_DISHES));
        }};
        Mockito.when(foodRepository.findAllById(Mockito.anyIterable()))
                .thenReturn(foods);

        final Order order = new Order(
                1L,
                TIMESTAMP,
                user,
                true,
                table,
                OrderStatus.USER_CONFIRMED,
                foods,
                3.0
        );
        Mockito.when(orderRepository.save(Mockito.any()))
                .thenReturn(order);

        Mockito.when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        Mockito.when(orderRepository.findOrdersByStatusBetween(OrderStatus.PREPARING, OrderStatus.DONE))
                .thenReturn(new HashSet<>());

        Mockito.when(orderRepository.findByStatus(Mockito.any(OrderStatus.class)))
                .thenReturn(new ArrayList<>());

        foodIdList = new ArrayList<Long>() {{
            add(1L);
            add(2L);
        }};

        answer = new OrderDTO(
                1L,
                1L,
                TIMESTAMP,
                OrderStatus.USER_CONFIRMED,
                foodIdList,
                UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"),
                true,
                3.0
        );
    }

    @Test
    void createOrderTest() {
        val inputDTO = new OrderDTO(
                null,
                1L,
                null,
                OrderStatus.USER_CONFIRMED,
                foodIdList,
                UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"),
                true,
                3.0
        );
        final OrderDTO result = orderService.createNewOrder(inputDTO);

        assertEquals(answer, result);

        val inputDTOWithNotExistUser = new OrderDTO(
                null,
                3L,
                null,
                OrderStatus.USER_CONFIRMED,
                foodIdList,
                UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"),
                true,
                3.0
        );
        assertThrows(IllegalStateException.class, () -> orderService.createNewOrder(inputDTOWithNotExistUser));

        val inputDTOWithNotExistTable = new OrderDTO(
                null,
                1L,
                null,
                OrderStatus.USER_CONFIRMED,
                foodIdList,
                UUID.randomUUID(),
                true,
                3.0
        );
        assertThrows(IllegalStateException.class, () -> orderService.createNewOrder(inputDTOWithNotExistTable));
    }

    @Test
    void findByIdTest() {
        final OrderDTO result = orderService.findOrderById(1L);

        assertEquals(answer, result);
    }

    /*@Test
    void changeStatusTest() {
        final OrderDTO result = orderService.updateOrder(1L, OrderStatus.PREPARING);

        assertEquals(OrderStatus.PREPARING, result.getStatus());
    }*/

    @Test
    void getOrdersForWaitersTest() {
        final Collection<OrderDTO> result = orderService.getOrdersForWaiters();

        assertEquals(0, result.size());
    }

    /*@Test
    void getOrdersByStatusTest() {
        final Collection<OrderDTO> result = orderService.getOrders(OrderStatus.PREPARING);

        assertEquals(0, result.size());
    }*/

}