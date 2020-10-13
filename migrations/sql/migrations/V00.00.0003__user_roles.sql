-- Roles

CREATE TABLE user_roles
(
    user_role_id INTEGER,
    role_name VARCHAR(24) NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_role_id),
    CONSTRAINT uq_user_roles_role_name UNIQUE (role_name)
);