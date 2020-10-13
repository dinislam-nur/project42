package ru.innopolis.stc27.maslakov.enterprise.project42.entities.food;


import lombok.*;
import ru.innopolis.stc27.maslakov.enterprise.project42.entities.order.Order;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "foods")
public class Food {

    @Id
    @Column(name = "food_id")
    @GeneratedValue(generator = "FOOD_ID_GENERATOR", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "FOOD_ID_GENERATOR", allocationSize = 1, sequenceName = "foods_food_id_seq")
    private Long id;

    @Column(name = "food_name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "picture")
    private String picture;

    @Convert(converter = FoodCategoryAttributeConverter.class)
    @Column(name = "food_category_id")
    private FoodCategory foodCategory;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "foods2order",
//            joinColumns = {
//                    @JoinColumn(name = "food_id")
//            },
//            inverseJoinColumns = {
//                    @JoinColumn(name = "order_id")
//            }
//    )
//    @ToString.Exclude
//    private List<Order> orders;
}


