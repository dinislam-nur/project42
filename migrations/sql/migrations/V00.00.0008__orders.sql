-- Orders

CREATE TABLE orders
(
    order_id BIGSERIAL,
    order_time TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL,
    is_payed BOOLEAN NOT NULL,
    table_id UUID NOT NULL,
    order_status_id INTEGER NOT NULL,
    total_sum DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_orders_order_id PRIMARY KEY (order_id),
    CONSTRAINT fk_orders_users_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_orders_tables_table_id FOREIGN KEY (table_id) REFERENCES tables (table_id),
    CONSTRAINT fk_orders_order_statuses FOREIGN KEY (order_status_id) REFERENCES order_statuses (order_status_id)
);