package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findByUser(User user);

    @Query("SELECT o FROM Order o WHERE o.user.id = :user_id")
    Page<Order> findByUserId(@Param("user_id") Long userId, Pageable pageable);

    List<Order> findByUserAndPayedFalse(User user);

    List<Order> findByTable(Table table);

    List<Order> findByTableAndPayedFalse(Table table);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    List<Order> findOrdersByPayedFalse();

    Set<Order> findOrdersByStatusBetween(OrderStatus from, OrderStatus to);
}
