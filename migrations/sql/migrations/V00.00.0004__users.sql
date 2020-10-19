-- Users

CREATE TABLE users
(
  user_id BIGSERIAL,
  login TEXT NOT NULL,
  password TEXT NOT NULL,
  user_role_id INTEGER NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (user_id),
  CONSTRAINT fk_user_roles FOREIGN KEY (user_role_id) REFERENCES user_roles (user_role_id),
  CONSTRAINT uq_users_login UNIQUE (login)
);