-- Session table

CREATE TABLE sessions
(
    session_id BIGSERIAL,
    token TEXT NOT NULL,
    timeout TIMESTAMP NOT NULL,
    table_id UUID,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_sessions_session_id PRIMARY KEY (session_id),
    CONSTRAINT uq_sessions_token UNIQUE (token),
    CONSTRAINT fk_sessions_tables_table_id FOREIGN KEY (table_id) REFERENCES tables (table_id) ON DELETE CASCADE,
    CONSTRAINT fk_sessions_users_user_id FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);