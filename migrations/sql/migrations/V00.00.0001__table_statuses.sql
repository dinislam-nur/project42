--Table's status table

CREATE TABLE table_statuses
(
    table_status_id BIGINT NOT NULL,
    status_name VARCHAR(24) NOT NULL,
    CONSTRAINT pk_table_statuses PRIMARY KEY (table_status_id)
);