package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.List;

public interface OrderRepository {

    List<Order> findAll();

    Order findById(int id);

    List<Order> findByGuest(User user);

    List<Order> findNotPayedByGuest(User user);

    List<Order> findByTable(Table table);

    List<Order> findNotPayedByTable(Table table);

    List<Order> findByFood(Food food);

    List<Order> findByStatus(OrderStatus status);

    Order save(Order order);

    Order delete(Order order);
}
