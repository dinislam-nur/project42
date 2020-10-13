package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.junit.jupiter.api.BeforeAll;
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
class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodRepository;

//    @Autowired
//    private OrderRepository orderRepository;

    private static List<Food> answer;

    @BeforeAll
    static void beforeAll() {
        answer = new ArrayList<Food>() {{
            add(
                    Food.builder()
                            .id(1L)
                            .name("compot")
                            .picture("test.ru")
                            .price(1.0)
                            .foodCategory(FoodCategory.DRINK)
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
    @Disabled
    void findAllTest() {

        final List<Food> foods = foodRepository.findAll();
        foods.forEach(food -> System.out.println(food + " поиск всех"));

        for (int i = 0; i < answer.size(); i++) {
            assertEquals(answer.get(i), foods.get(i));
        }
    }

    @Test
    @Disabled
    void findByIdTest() {
        final Food food = foodRepository.findById(1L).orElse(null);
        System.out.println(food + " поиск по id");

        assertEquals(answer.get(0), food);
    }

    @Test
    @Disabled
    void findByNameTest() {
        final Food compot = foodRepository.findByName("compot").orElse(null);
        final Food borsh = foodRepository.findByName("borsh").orElse(null);
        System.out.println(compot + " поиск по имени");
        System.out.println(borsh + " поиск по имени");

        assertEquals(answer.get(0), compot);
        assertEquals(answer.get(1), borsh);
    }

    @Test
    @Disabled
    void findByFoodCategoryTest() {
        final List<Food> hotDishes = foodRepository.findByFoodCategory(FoodCategory.HOT_DISHES);
        hotDishes.forEach(food -> System.out.println(food + " поиск по категории блюда"));

        assertEquals(answer.get(1), hotDishes.get(0));
    }

//    void findByOrderTest() {
//
//    }

    @Test
    @Disabled
    void saveAndDeleteTest() {
        final Food salat = Food.builder()
                .id(null)
                .name("salat")
                .picture("test.ru")
                .price(1.0)
                .foodCategory(FoodCategory.SOMETHING_ELSE)
                .build();

        final Food saved = foodRepository.save(salat);
        salat.setId(saved.getId());

        System.out.println(saved + " сохраненное блюдо");
        assertEquals(salat, saved);

        salat.setPrice(0.5);
        final Food updated = foodRepository.save(salat);
        System.out.println(updated + " обновленное блюдо");

        assertEquals(salat, updated);

        foodRepository.delete(salat);
    }
}