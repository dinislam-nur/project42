package ru.innopolis.stc27.maslakov.enterprise.project42.entities.food;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Food {

    private Integer id;
    private String name;
    private double price;
    private FoodCategory foodCategory;

}
