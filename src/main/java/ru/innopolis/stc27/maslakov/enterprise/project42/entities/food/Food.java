package ru.innopolis.stc27.maslakov.enterprise.project42.entities.food;

import lombok.Data;

@Data
public class Food {

    private final int id;
    private String name;
    private int price;
    private FoodCategory foodCategory;
}
