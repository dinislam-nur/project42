package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    private static Order currentOrder;

    @BeforeAll
    static void beforeAll() {
        currentOrder = Order.builder()
                .id(1L)
                .table(
                        Table.builder()
                                .id(1L)
                                .number(1)
                                .status(TableStatus.NOT_RESERVED)
                                .build()
                )
                .user(
                        User.builder()
                                .id(1L)
                                .login("admin")
                                .password("admin")
                                .salt(123)
                                .role(Role.GUEST)
                                .build()
                )
                .isPayed(false)
                .status(OrderStatus.USER_CONFIRMED)
                .build();
    }

    @Test
    void findAllTest() {
        final List<Order> orders = orderRepository.findAll();
        orders.forEach(order -> System.out.println(order + " поиск всех"));

        final Order order = orders.get(0);
        currentOrder.setOrderTime(order.getOrderTime());
        currentOrder.setFoods(order.getFoods());

        assertEquals(currentOrder, order);
    }

    @Test
    void findByIdTest() {
        final Order order = orderRepository.findById(1L).orElse(null);
        System.out.println(order + " поиск по id");

        currentOrder.setOrderTime(order.getOrderTime());
        currentOrder.setFoods(order.getFoods());

        assertEquals(currentOrder, order);
    }

    @Test
    void findByUserTest() {
        final List<Order> orders = orderRepository.findByUser(currentOrder.getUser());
        orders.forEach(order -> System.out.println(order + " поиск по пользователю"));
        final Order order = orders.get(0);
        currentOrder.setOrderTime(order.getOrderTime());
        currentOrder.setFoods(order.getFoods());

        assertEquals(currentOrder, order);
    }

//    @Test
//    void findByUserAndPayedFalseTest() {
//        final List<Order> orders = orderRepository.findByUserAndPayedFalse(currentOrder.getUser());
//        orders.forEach(order -> System.out.println(order + " поиск неоплаченных заказов по пользователю"));
//        final Order order = orders.get(0);
//        currentOrder.setOrderTime(order.getOrderTime());
//        currentOrder.setFoods(order.getFoods());
//
//        assertEquals(currentOrder, order);
//    }

    @Test
    void findByTableTest() {
        final List<Order> orders = orderRepository.findByTable(currentOrder.getTable());
        orders.forEach(order -> System.out.println(order + " поиск по столу"));
        final Order order = orders.get(0);
        currentOrder.setOrderTime(order.getOrderTime());
        currentOrder.setFoods(order.getFoods());

        assertEquals(currentOrder, order);
    }

//    @Test
//    void findByTableAndPayedFalseTest() {
//        final List<Order> orders = orderRepository.findByTableAndPayedFalse(currentOrder.getTable());
//        orders.forEach(order -> System.out.println(order + " поиск неоплаченных заказов по столу"));
//        final Order order = orders.get(0);
//        currentOrder.setOrderTime(order.getOrderTime());
//        currentOrder.setFoods(order.getFoods());
//
//        assertEquals(currentOrder, order);
//    }

    @Test
    void findByStatusTest() {
        final List<Order> orders = orderRepository.findByStatus(currentOrder.getStatus());
        orders.forEach(order -> System.out.println(order + " поиск по статусу"));
        final Order order = orders.get(0);
        currentOrder.setOrderTime(order.getOrderTime());
        currentOrder.setFoods(order.getFoods());

        assertEquals(currentOrder, order);
    }

//    @Test
//    void findByPayedFalse() {
//        final List<Order> orders = orderRepository.findOrdersByPayedFalse();
//        orders.forEach(order -> System.out.println(order + " поиск всех неоплаченных заказов"));
//        final Order order = orders.get(0);
//        currentOrder.setOrderTime(order.getOrderTime());
//        currentOrder.setFoods(order.getFoods());
//
//        assertEquals(currentOrder, order);
//    }

    @Test
//    @Transactional
    void saveAndDeleteTest() {
        final List<Food> foods = new ArrayList<>();
        foods.add(foodRepository.findByName("compot").orElse(null));
        foods.add(foodRepository.findByName("borsh").orElse(null));
        final Order newOrder = Order.builder()
                .id(null)
                .table(tableRepository.findByNumber(1).orElse(null))
                .user(userRepository.findById(1L).orElse(null))
                .isPayed(false)
                .status(OrderStatus.USER_CONFIRMED)
                .orderTime(Timestamp.valueOf(LocalDateTime.now()))
                .foods(foods)
                .build();


        final Order saved = orderRepository.save(newOrder);
        newOrder.setId(saved.getId());
        System.out.println(saved + " запись сохранена");

        assertEquals(newOrder, saved);

        saved.setStatus(OrderStatus.STAFF_CONFIRMED);
        final Order updated = orderRepository.save(saved);
        System.out.println(updated + " запись обновлена");

        assertEquals(saved, updated);

//        orderRepository.delete(updated);
    }
}