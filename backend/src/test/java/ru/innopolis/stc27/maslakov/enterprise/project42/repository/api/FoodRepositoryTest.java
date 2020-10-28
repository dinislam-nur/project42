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
//import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
//import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Slf4j
//@Disabled
//@SpringBootTest
//class FoodRepositoryTest {
//
//    private final FoodRepository foodRepository;
//    private static Flyway flyway = null;
//
//    private List<Food> answer;
//
//    @Autowired
//    FoodRepositoryTest(FoodRepository foodRepository,
//                       Flyway flyway1) {
//        this.foodRepository = foodRepository;
//        flyway = flyway1;
//    }
//
//    @SneakyThrows
//    @BeforeEach
//    void setUp() {
//        flyway.clean();
//        flyway.migrate();
//        @Cleanup val reader = new BufferedReader(
//                new FileReader("src/test/resources/food_pictures/Beef_stroganoff_with_champignons.txt"));
//        val picture = reader.readLine();
//        answer = new ArrayList<Food>() {{
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
//        final Iterable<Food> foods = foodRepository.findAll();
//        foods.forEach(food -> System.out.println(food + " - поиск всех"));
//        final Food result = foods.iterator().next();
//
//        assertEquals(answer.get(0), result);
//    }
//
//    @Test
//    void findByIdTest() {
//        final Food food = foodRepository.findById(1L).orElse(null);
//        System.out.println(food + " - поиск по id");
//
//        assertEquals(answer.get(0), food);
//    }
//
//    @Test
//    void findAllByIdTest() {
//        final List<Long> foodsId = new ArrayList<Long>() {{ add(1L); add(2L);}};
//        final Iterable<Food> foodsIterable = foodRepository.findAllById(foodsId);
//        final ArrayList<Food> foods = new ArrayList<>();
//        foodsIterable.forEach(foods::add);
//
//        for (int i = 0; i < answer.size(); i++) {
//            assertEquals(answer.get(i), foods.get(i));
//        }
//    }
//
//    @Test
//    void findByNameTest() {
//        final Food answerFood = answer.get(0);
//        final Food result = foodRepository.findByName(answerFood.getName()).orElse(null);
//        log.info(result + " - поиск по имени");
//
//        assertEquals(answerFood, result);
//    }
//
//    @Test
//    void insertTest() {
//        final Food salat = Food.builder()
//                .id(null)
//                .name("salat")
//                .picture("test.ru")
//                .price(1.0)
//                .foodCategory(FoodCategory.SALADS)
//                .build();
//
//        final Food saved = foodRepository.save(salat);
//        salat.setId(saved.getId());
//        log.info(saved + " - запись сохранена");
//
//        assertEquals(salat, saved);
//    }
//
//    @Test
//    void updateTest() {
//        final Food compot = answer.get(0);
//        compot.setPrice(1.6);
//        final Food updated = foodRepository.save(compot);
//        log.info(updated + " - запись обновлена");
//
//        assertEquals(compot, updated);
//    }
//
//    @Test
//    void deleteTest() {
//        final Food food = answer.get(0);
//        foodRepository.delete(food);
//        log.info(food + " - запись удалена");
//
//        assertNull(foodRepository.findById(food.getId()).orElse(null));
//    }
//}