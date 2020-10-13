--Table's status table

CREATE TABLE table_statuses
(
    table_status_id INTEGER,
    table_status_name VARCHAR(24) NOT NULL,
    CONSTRAINT pk_table_statuses PRIMARY KEY (table_status_id),
    CONSTRAINT uq_table_status_name UNIQUE (table_status_name)
);