--Tables table

CREATE TABLE tables
(
    table_id BIGSERIAL,
    number INTEGER UNIQUE NOT NULL,
    table_status_id BIGINT NOT NULL,
    CONSTRAINT pk_tables PRIMARY KEY (table_id),
    CONSTRAINT fk_table_statuses FOREIGN KEY (table_status_id) REFERENCES table_statuses (table_status_id)
);