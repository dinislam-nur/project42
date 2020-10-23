package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Disabled
class FoodRepositoryTest {

    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;
    private final Flyway flyway;

    private List<Food> answer;

    @Autowired
    FoodRepositoryTest(FoodRepository foodRepository,
                       OrderRepository orderRepository,
                       Flyway flyway) {
        this.foodRepository = foodRepository;
        this.orderRepository = orderRepository;
        this.flyway = flyway;
    }

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
        answer = new ArrayList<Food>() {{
            add(
                    Food.builder()
                            .id(1L)
                            .name("compot")
                            .picture("test.ru")
                            .price(1.0)
                            .foodCategory(FoodCategory.DRINKS)
                            .build()
            );
            add(
                    Food.builder()
                            .id(2L)
                            .name("borsh")
                            .price(2.0)
                            .picture("test.ru")
                            .foodCategory(FoodCategory.HOT_DISHES)
                            .build()
            );
        }};
    }

    @Test
    void findAllTest() {
        final Iterable<Food> foods = foodRepository.findAll();
        foods.forEach(food -> System.out.println(food + " - поиск всех"));
        final Food result = foods.iterator().next();

        assertEquals(answer.get(0), result);
    }

    @Test
    void findByIdTest() {
        final Food food = foodRepository.findById(1L).orElse(null);
        System.out.println(food + " - поиск по id");

        assertEquals(answer.get(0), food);
    }

    @Test
    void findAllByIdTest() {
        final List<Long> foodsId = new ArrayList<Long>() {{ add(1L); add(2L);}};
        final Iterable<Food> foodsIterable = foodRepository.findAllById(foodsId);
        final ArrayList<Food> foods = new ArrayList<>();
        foodsIterable.forEach(foods::add);

        for (int i = 0; i < answer.size(); i++) {
            assertEquals(answer.get(i), foods.get(i));
        }
    }

    @Test
    void findByNameTest() {
        final Food compot = foodRepository.findByName("compot").orElse(null);
        final Food borsh = foodRepository.findByName("borsh").orElse(null);
        System.out.println(compot + " - поиск по имени");
        System.out.println(borsh + " - поиск по имени");

        assertEquals(answer.get(0), compot);
        assertEquals(answer.get(1), borsh);
    }

    @Test
    void findByFoodCategoryTest() {
        final List<Food> hotDishes = foodRepository.findByFoodCategory(FoodCategory.HOT_DISHES);
        hotDishes.forEach(food -> System.out.println(food + " - поиск по категории блюда"));

        assertEquals(answer.get(1), hotDishes.get(0));
    }

    @Test
    void insertTest() {
        final Food salat = Food.builder()
                .id(null)
                .name("salat")
                .picture("test.ru")
                .price(1.0)
                .foodCategory(FoodCategory.SALADS)
                .build();

        final Food saved = foodRepository.save(salat);
        salat.setId(saved.getId());
        System.out.println(saved + " - запись сохранена");

        assertEquals(salat, saved);
    }

    @Test
    void updateTest() {
        final Food compot = answer.get(0);
        compot.setPrice(1.6);
        final Food updated = foodRepository.save(compot);
        System.out.println(updated + " - запись обновлена");

        assertEquals(compot, updated);
    }

    @Test
    void deleteTest() {
        final Food borsh = answer.get(1);
        foodRepository.delete(borsh);
        System.out.println(borsh + " - запись удалена");

        assertNull(foodRepository.findById(borsh.getId()).orElse(null));
    }
}