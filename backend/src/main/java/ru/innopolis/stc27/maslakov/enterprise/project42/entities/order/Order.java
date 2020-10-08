package ru.innopolis.stc27.maslakov.enterprise.project42.entities.order;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;

import java.sql.Date;
import java.util.List;

@Builder
@ToString
@EqualsAndHashCode
public class Order {

    @Getter
    private Integer id;

    @Getter
    private Date orderTime;

    @Getter
    private User user;

    private boolean isPayed;

    @Getter
    private Table table;

    private List<Food> foods;

    public void paid() {
        isPayed = true;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public double sum() {
        return foods.stream()
                .mapToDouble(Food::getPrice)
                .sum();
    }

}
