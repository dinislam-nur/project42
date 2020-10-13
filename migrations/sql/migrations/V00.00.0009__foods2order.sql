-- Foods to order

CREATE TABLE foods2order
(
    order_id BIGINT NOT NULL,
    food_id BIGINT NOT NULL,
    CONSTRAINT pk_foods2order PRIMARY KEY (order_id, food_id),
    CONSTRAINT fk_foods2order_orders_order_id FOREIGN KEY (order_id) REFERENCES orders (order_id),
    CONSTRAINT fk_foods2order_foods_food_id FOREIGN KEY (food_id) REFERENCES foods (food_id)
);