package ru.innopolis.stc27.maslakov.enterprise.project42.entities.order;

import lombok.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@javax.persistence.Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id", nullable = false)
    @GeneratedValue(generator = "ORDER_ID_GENERATOR", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "ORDER_ID_GENERATOR", allocationSize = 1, sequenceName = "orders_order_id_seq")
    private Long id;

    @Column(name = "order_time", nullable = false)
    private Timestamp orderTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_payed", nullable = false)
    private boolean payed;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    @Convert(converter = OrderStatusAttributeConverter.class)
    @Column(name = "order_status_id", nullable = false)
    private OrderStatus status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "foods2order",
            joinColumns = {
                    @JoinColumn(name = "order_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "food_id")
            }
    )
    private List<Food> foods;

    @JoinColumn(name = "total_sum", nullable = false)
    Double totalSum;

    @PrePersist
    public void prepare() {
        orderTime = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (payed != order.payed) return false;
        if (!Objects.equals(id, order.id)) return false;
        if (!Objects.equals(orderTime, order.orderTime)) return false;
        if (!Objects.equals(user, order.user)) return false;
        if (!Objects.equals(table, order.table)) return false;
        if (status != order.status) return false;
        if (foods != null) {
            boolean checker = true;
            if (order.foods != null) {
                for (int i = 0; i < foods.size(); i++) {
                    checker &= foods.get(i).equals(order.foods.get(i));
                }
                return checker;
            }
        }
        return false;
    }

}


