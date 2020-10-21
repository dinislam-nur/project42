package ru.innopolis.stc27.maslakov.enterprise.project42.controllers.food;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.services.food.FoodService;

@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping(value = "/foods")
    private Page<Food> getFoods(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return foodService.getPageFoods(page, size);
    }

}
