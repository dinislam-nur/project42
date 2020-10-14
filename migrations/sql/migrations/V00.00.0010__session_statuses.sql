-- Session statuses table

CREATE TABLE session_statuses
(
    session_status_id INTEGER,
    session_status_name VARCHAR(24) NOT NULL,
    CONSTRAINT pk_session_statuses_session_status_id PRIMARY KEY (session_status_id),
    CONSTRAINT uq_session_statusses_session_status_name UNIQUE (session_status_name)
);