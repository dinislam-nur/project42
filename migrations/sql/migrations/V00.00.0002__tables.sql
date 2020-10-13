--Tables table

CREATE TABLE tables
(
    table_id BIGSERIAL,
    number INTEGER NOT NULL,
    table_status_id INTEGER NOT NULL,
    CONSTRAINT pk_tables PRIMARY KEY (table_id),
    CONSTRAINT fk_table_statuses FOREIGN KEY (table_status_id) REFERENCES table_statuses (table_status_id),
    CONSTRAINT uq_tables_number UNIQUE (number)
);