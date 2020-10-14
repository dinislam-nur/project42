-- Order statuses

CREATE TABLE order_statuses
(
    order_status_id INTEGER NOT NULL,
    order_status_name VARCHAR(24) NOT NULL,
    CONSTRAINT pk_order_statuses_order_status_id PRIMARY KEY (order_status_id),
    CONSTRAINT uq_order_status_name UNIQUE (order_status_name)
);