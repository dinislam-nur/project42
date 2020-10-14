package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByUserAndPayedFalse(User user);

    List<Order> findByTable(Table table);

    List<Order> findByTableAndPayedFalse(Table table);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findOrdersByPayedFalse();
}
