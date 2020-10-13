package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findAll();

    Optional<Order> findById(Long id);

    List<Order> findByUser(User user);

//    List<Order> findByUserAndPayedFalse(User user);

    List<Order> findByTable(Table table);

//    List<Order> findByTableAndPayedFalse(Table table);

    List<Order> findByStatus(OrderStatus status);

//    List<Order> findOrdersByPayedFalse();

    Order save(Order order);

    void delete(Order order);
}
