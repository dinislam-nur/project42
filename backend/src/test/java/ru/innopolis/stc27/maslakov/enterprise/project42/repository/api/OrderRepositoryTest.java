//package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;
//
//import lombok.Cleanup;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import lombok.val;
//import org.flywaydb.core.Flyway;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
//import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;
//import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
//import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
//import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
//import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.TableStatus;
//import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Role;
//import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Slf4j
//@Disabled
//@SpringBootTest
//class OrderRepositoryTest {
//
//    private final OrderRepository orderRepository;
//    private static Flyway flyway = null;
//    private PageRequest pageRequest;
//
//    private Order answer;
//
//    @Autowired
//    OrderRepositoryTest(OrderRepository orderRepository, Flyway flywayBean) {
//        this.orderRepository = orderRepository;
//        flyway = flywayBean;
//    }
//
//    @SneakyThrows
//    @BeforeEach
//    void setUp() {
//        flyway.clean();
//        flyway.migrate();
//
//        pageRequest = PageRequest.of(0, 10, Sort.by("orderTime"));
//        @Cleanup val reader = new BufferedReader(
//                new FileReader("src/test/resources/food_pictures/Beef_stroganoff_with_champignons.txt"));
//        val picture = reader.readLine();
//        final List<Food> foods = new ArrayList<Food>() {{
//            add(
//                    Food.builder()
//                            .id(1L)
//                            .name("Бефстроганов с шампиньонами")
//                            .picture(picture)
//                            .price(42)
//                            .foodCategory(FoodCategory.HOT_DISHES)
//                            .build()
//            );
//        }};
//        answer = Order.builder()
//                .id(1L)
//                .table(
//                        Table.builder()
//                                .id(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"))
//                                .number(1)
//                                .status(TableStatus.NOT_RESERVED)
//                                .build()
//                )
//                .user(
//                        User.builder()
//                                .id(1L)
//                                .login("user")
//                                .password("$2y$10$MfJEpQhrvAo0M4lJXMfFCuTOtGyy8x79PpavQ7T.GnMPorKbTFzHy")
//                                .role(Role.ROLE_GUEST)
//                                .build()
//                )
//                .payed(false)
//                .status(OrderStatus.USER_CONFIRMED)
//                .foods(foods)
//                .orderTime(Timestamp.valueOf("2020-10-15 00:00:00.200000"))
//                .totalSum(42.0)
//                .build();
//    }
//
//    @AfterAll
//    static void afterAll() {
//        flyway.clean();
//        flyway.migrate();
//    }
//
//    @Test
//    void findAllTest() {
//        final Iterable<Order> orders = orderRepository.findAll();
//        orders.forEach(order -> System.out.println(order + " - поиск всех"));
//
//        final Order result = orders.iterator().next();
//
//        assertEquals(answer, result);
//    }
//
//    @Test
//    void findByIdTest() {
//        final Order result = orderRepository.findById(1L).orElse(null);
//        log.info(result + " - поиск по id");
//
//        assertEquals(answer, result);
//    }
//
//    @Test
//    void findByUserIdTest() {
//        val orders = orderRepository.findByUserId(answer.getUser().getId(), pageRequest);
//        orders.forEach(order -> log.info(order + " - поиск по пользователю"));
//        final Order result = orders.iterator().next();
//
//        assertEquals(answer, result);
//    }
//
//    @Test
//    void findByUserAndPayedFalseTest() {
//        final List<Order> orders = orderRepository.findByUserAndPayedFalse(answer.getUser());
//        orders.forEach(order -> log.info(order + " - поиск неоплаченных заказов по пользователю"));
//        final Order result = orders.get(0);
//
//        assertEquals(answer, result);
//    }
//
//    @Test
//    void findByTableTest() {
//        final List<Order> orders = orderRepository.findByTable(answer.getTable());
//        orders.forEach(order -> log.info(order + " - поиск по столу"));
//        final Order result = orders.get(0);
//
//        assertEquals(answer, result);
//    }
//
//    @Test
//    void findByTableAndPayedFalseTest() {
//        final List<Order> orders = orderRepository.findByTableAndPayedFalse(answer.getTable());
//        orders.forEach(order -> log.info(order + " - поиск неоплаченных заказов по столу"));
//        final Order result = orders.get(0);
//
//        assertEquals(answer, result);
//    }
//
//    @Test
//    void findByStatusTest() {
//        final Page<Order> orders = orderRepository.findByStatus(answer.getStatus(), pageRequest);
//        orders.forEach(order -> log.info(order + " - поиск по статусу"));
//        final Order result = orders.iterator().next();
//
//        assertEquals(answer, result);
//    }
//
//    @Test
//    void findByPayedFalse() {
//        final List<Order> orders = orderRepository.findOrdersByPayedFalse();
//        orders.forEach(order -> log.info(order + " - поиск всех неоплаченных заказов"));
//        final Order result = orders.get(0);
//
//        assertEquals(answer, result);
//    }
//
//    @Test
//    void insertTest() {
//        final List<Food> foods = new ArrayList<Food>() {{
//            add(
//                    Food.builder()
//                            .id(2L)
//                            .name("borsh")
//                            .price(2.0)
//                            .picture("test.ru")
//                            .foodCategory(FoodCategory.HOT_DISHES)
//                            .build()
//            );
//        }};
//        final Order newOrder = Order.builder()
//                .id(null)
//                .table(
//                        Table.builder()
//                                .id(UUID.fromString("57874486-11f8-11eb-adc1-0242ac120002"))
//                                .number(1)
//                                .status(TableStatus.NOT_RESERVED)
//                                .build()
//                )
//                .user(
//                        User.builder()
//                                .id(1L)
//                                .login("user")
//                                .password("user")
//                                .role(Role.ROLE_GUEST)
//                                .build()
//                )
//                .payed(false)
//                .status(OrderStatus.USER_CONFIRMED)
//                .foods(foods)
//                .totalSum(3.0)
//                .build();
//
//        final Order saved = orderRepository.save(newOrder);
//        newOrder.setId(saved.getId());
//        System.out.println(saved + " - запись сохранена");
//
//        assertEquals(newOrder, saved);
//    }
//
//    @Test
//    void updateTest() {
//        answer.setStatus(OrderStatus.CANCELLED);
//        final Order updated = orderRepository.save(answer);
//        System.out.println(updated + " - запись обновлена");
//
//        assertEquals(answer, updated);
//    }
//
//    @Test
//    void deleteTest() {
//        orderRepository.delete(answer);
//        System.out.println(answer + " - запись удалена");
//
//        assertNull(orderRepository.findById(answer.getId()).orElse(null));
//    }
//
//    @Test
//    void findOrderByStatusBetweenTest() {
//        final Set<Order> orders = orderRepository
//                .findOrdersByStatusBetween(OrderStatus.PREPARING, OrderStatus.DONE);
//
//        assertEquals(2, orders.size());
//    }
//}