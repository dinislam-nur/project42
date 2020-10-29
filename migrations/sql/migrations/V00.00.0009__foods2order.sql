-- Foods to order

CREATE TABLE foods2order
(
    id BIGSERIAL NOT NULL,
    order_id BIGINT NOT NULL,
    food_id BIGINT NOT NULL,
    CONSTRAINT pk_foods2order PRIMARY KEY (id),
    CONSTRAINT fk_foods2order_orders_order_id FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    CONSTRAINT fk_foods2order_foods_food_id FOREIGN KEY (food_id) REFERENCES foods (food_id) ON DELETE CASCADE
);