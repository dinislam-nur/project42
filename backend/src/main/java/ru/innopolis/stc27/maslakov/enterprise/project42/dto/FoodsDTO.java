package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Builder;
import lombok.Value;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;

@Value
@Builder
public class FoodsDTO {

    Long id;

    String name;

    FoodCategory category;

    Double price;

    String picture;
}
