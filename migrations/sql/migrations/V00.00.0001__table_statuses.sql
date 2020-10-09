--Table's status table

CREATE TABLE table_statuses
(
    table_status_id BIGINT,
    status_name VARCHAR(24) UNIQUE NOT NULL,
    CONSTRAINT pk_table_statuses PRIMARY KEY (table_status_id)
);