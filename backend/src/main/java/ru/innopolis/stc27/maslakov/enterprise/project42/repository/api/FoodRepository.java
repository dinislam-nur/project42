package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;

import java.util.List;
import java.util.Optional;

public interface FoodRepository {

    List<Food> findAll();

    Food findById(int id);

    Optional<Food> findByName(String name);

    Optional<List<Food>> findByCategory(FoodCategory category);

    Optional<List<Food>> findByOrder(Order order);

    Food update(Food updatedFood);

    Food insert(Food newFood);

    Food delete(Food candidate);
}
