package ru.innopolis.stc27.maslakov.enterprise.project42.dto;

import lombok.Value;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.FoodCategory;

import javax.validation.constraints.NotNull;

@Value
public class FoodDTO {

    Long id;

    @NotNull(message = "Пустое поле 'Название блюда'")
    String name;

    @NotNull(message = "Пустое поле 'Категория блюда'")
    FoodCategory category;

    @NotNull(message = "Пустое поле 'Цена блюда'")
    Double price;

    @NotNull(message = "Пустое поле 'Изображение блюда'")
    String picture;

}
