package ru.innopolis.stc27.maslakov.enterprise.project42.entities.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Guest;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;

import java.sql.Date;

@Builder
public class Order {

    @Getter
    private int id;

    @Getter
    private Date orderTime;

    @Getter
    private Guest guest;

    private boolean isPayed;

    @Getter
    @Setter
    private Table table;

    public void paid() {
        isPayed = true;
    }

    public boolean isPayed() {
        return isPayed;
    }



}
