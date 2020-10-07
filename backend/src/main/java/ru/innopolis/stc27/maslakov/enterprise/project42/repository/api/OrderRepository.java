package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.Guest;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    List<Order> findAll();

    Order findById(int id);

    Optional<List<Order>> findByGuest(Guest guest);

    Optional<List<Order>> findNotPayedByGuest(Guest guest);

    Optional<List<Order>> findByTable(Table table);

    Optional<List<Order>> findNotPayedByTable(Table table);

    Optional<List<Order>> findByFood(Food food);

    Optional<List<Order>> findByStatus(OrderStatus status);

    Order update(Order order);

    Order insert(Order order);

    Order delete(Order order);
}
