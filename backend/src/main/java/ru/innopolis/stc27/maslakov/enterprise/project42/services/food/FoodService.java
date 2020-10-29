package ru.innopolis.stc27.maslakov.enterprise.project42.services.food;

import org.springframework.data.domain.Page;
import ru.innopolis.stc27.maslakov.enterprise.project42.dto.FoodDTO;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;

public interface FoodService {

    Page<Food> getPageFoods(Integer page, Integer size, FoodCategory foodCategory);

    void createOrUpdateFood(FoodDTO foodDTO);

    void deleteFood(Long foodId);

    FoodDTO getFood(Long foodId);

}
