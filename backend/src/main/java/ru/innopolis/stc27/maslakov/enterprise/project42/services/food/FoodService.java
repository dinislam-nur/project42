package ru.innopolis.stc27.maslakov.enterprise.project42.services.food;

import org.springframework.data.domain.Page;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;

public interface FoodService {

    Page<Food> getPageFoods(Integer page, Integer size);
}
