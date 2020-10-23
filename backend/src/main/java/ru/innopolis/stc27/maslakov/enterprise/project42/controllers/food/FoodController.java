package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.food;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.FoodsDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.food.FoodService;

@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping(value = "/foods/{category}")
    private Page<Food> getPageFoods(@PathVariable(value = "category") FoodCategory foodCategory,
                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return foodService.getPageFoods(page, size, foodCategory);
    }

    @PostMapping(value = "/foods/create")
    @ResponseStatus(HttpStatus.CREATED)
    private void getFood(@RequestBody FoodsDTO foodsDTO) {
        foodService.createOrUpdateFood(foodsDTO);
    }

    @PutMapping(value = "/foods")
    @ResponseStatus(HttpStatus.CREATED)
    private void update(@RequestBody FoodsDTO foodsDTO) {
        foodService.createOrUpdateFood(foodsDTO);
    }

    @DeleteMapping(value = "/foods/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void changeFood(@PathVariable(value = "id") Long foodID) {
        foodService.deleteFood(foodID);
    }

    @GetMapping(value = "/foods/get")
    private FoodsDTO getFood(@RequestParam(value = "id") Long foodsDTO) {
        return foodService.getFood(foodsDTO);
    }

}
