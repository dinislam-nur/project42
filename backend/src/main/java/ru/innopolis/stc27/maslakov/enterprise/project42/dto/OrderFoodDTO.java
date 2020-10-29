package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Value;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;

@Value
public class OrderFoodDTO {

    Long id;

    String name;

    FoodCategory category;

    Double price;

}
