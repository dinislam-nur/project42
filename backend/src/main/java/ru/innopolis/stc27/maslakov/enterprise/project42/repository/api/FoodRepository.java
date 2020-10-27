package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;

import java.util.Optional;

@Repository
public interface FoodRepository extends PagingAndSortingRepository<Food, Long> {

    Page<Food> findAllByFoodCategory(FoodCategory foodCategory, Pageable pageable);

    Optional<Food> findByName(String name);

    @Query("SELECT f.price FROM Food f WHERE f.id = :id")
    Double getPriceById(@Param("id") Long id);

}
