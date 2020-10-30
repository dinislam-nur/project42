package ru.innopolis.stc27.maslakov.enterprise.project42.services.order;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.OrderFoodDTO;
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

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    private static final Timestamp TIMESTAMP = Timestamp.valueOf("2020-10-15 00:00:00.000000");

    private OrderService orderService;
    private OrderRepository orderRepository;
    private OrderDTO stuffAnswer;
    private List<OrderFoodDTO> foodIdList;
    private Order order;

    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        val foodRepository = Mockito.mock(FoodRepository.class);
        orderService = new OrderServiceImpl(
                orderRepository,
                foodRepository);

        final User user = new User(1L, "user", "password", Role.ROLE_GUEST);

        final Table table = new Table(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"), 1, TableStatus.NOT_RESERVED);

        final List<Food> foods = new ArrayList<Food>() {{
            add(new Food(1L, "compot", 2.0, "test.ru", FoodCategory.DRINKS));
        }};
        Mockito.when(foodRepository.findAllById(Mockito.anyIterable()))
                .thenReturn(foods);

        order = new Order(
                1L,
                TIMESTAMP,
                user,
                true,
                table,
                OrderStatus.USER_CONFIRMED,
                foods,
                2.0
        );

        Mockito.when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        Mockito.when(orderRepository.findOrdersByStatusBetween(OrderStatus.PREPARING, OrderStatus.DONE))
                .thenReturn(new HashSet<>());

        Mockito.when(orderRepository.findByStatus(Mockito.any(OrderStatus.class), Mockito.any(Pageable.class)))
                .thenReturn(Page.empty());

        Mockito.when(orderRepository.findByUserId(Mockito.anyLong(), Mockito.any(Pageable.class)))
                .thenReturn(Page.empty());

        Mockito.when(orderRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(Page.empty());

        foodIdList = new ArrayList<OrderFoodDTO>() {{
            add(new OrderFoodDTO(
                    1L,
                    "compot",
                    FoodCategory.DRINKS,
                    2.0
            ));
        }};

        stuffAnswer = new OrderDTO(
                1L,
                1L,
                TIMESTAMP,
                OrderStatus.USER_CONFIRMED,
                foodIdList,
                UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"),
                table.getNumber(),
                true,
                2.0
        );
    }

//    @Test
//    void createOrderTest() {
//        val foodsId = new ArrayList<Long>() {{
//           add(1L);
//           add(2L);
//        }};
//        val inputDTO = new PrimaryOrderDTO(
//                foodsId,
//                UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002")
//        );
//        val resultId = orderService.createNewOrder(inputDTO);
//
//
//        assertEquals(stuffAnswer.getId(), resultId);
//
//    }

    @Test
    void findByIdTest() {
        val result = orderService.findOrderById(1L);

        assertEquals(stuffAnswer, result);
    }

    @Test
    void updateTest() {
        orderService.updateOrder(1L, stuffAnswer);

        Mockito.verify(orderRepository).save(order);

        assertThrows(RuntimeException.class, () -> orderService.updateOrder(2L, stuffAnswer));
    }

    @Test
    void getOrdersForWaitersTest() {
        val result = orderService.getOrdersForWaiters();

        assertEquals(0, result.size());
    }

//    @Test
//    void getOrdersTest() {
//        val page = 0;
//        val size = 10;
//        val resultByStatus = orderService.getOrders(OrderStatus.PREPARING,  page, size);
//
//        assertEquals(0, resultByStatus.getTotalElements());
//
//        val resultByUserId = orderService.getOrders(null, page, size);
//
//        assertEquals(0, resultByUserId.getTotalElements());
//
//        val resultAll = orderService.getOrders(null,  page, size);
//
//        assertEquals(0, resultAll.getTotalElements());
//    }

    @Test
    void deleteTest() {
        val id = 1L;
        orderService.deleteOrder(id);

        Mockito.verify(orderRepository).deleteById(id);
    }

}