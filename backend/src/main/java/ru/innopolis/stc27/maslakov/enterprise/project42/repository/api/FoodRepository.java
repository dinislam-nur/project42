package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;

@Repository
public interface FoodRepository extends PagingAndSortingRepository<Food, Long> {

    Page<Food> findAllByFoodCategory(FoodCategory foodCategory, Pageable pageable);

}
