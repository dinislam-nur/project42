-- Roles

CREATE TABLE user_roles
(
    user_role_id BIGINT,
    role_name VARCHAR(24) UNIQUE NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_role_id)
);