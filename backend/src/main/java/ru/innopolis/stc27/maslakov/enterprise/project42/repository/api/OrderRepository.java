package ru.innopolis.stc27.maslakov.enterprise.project42.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.OrderStatus;

import java.util.Set;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.user.id = :user_id")
    Page<Order> findByUserId(@Param("user_id") Long userId, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Set<Order> findOrdersByStatusBetween(OrderStatus from, OrderStatus to);

}
