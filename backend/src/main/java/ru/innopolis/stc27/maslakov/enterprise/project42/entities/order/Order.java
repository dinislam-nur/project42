package ru.innopolis.stc27.maslakov.enterprise.project42.entities.order;

import lombok.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.food.Food;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.users.User;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.table.Table;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


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
    private boolean isPayed;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    @Convert(converter = OrderStatusAttributeConverter.class)
    @Column(name = "order_status_id", nullable = false)
    private OrderStatus status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

//    public void paid() {
//        isPayed = true;
//    }
//
//    public boolean isPayed() {
//        return isPayed;
//    }

    public double sum() {
        return foods.stream()
                .mapToDouble(Food::getPrice)
                .sum();
    }

}


