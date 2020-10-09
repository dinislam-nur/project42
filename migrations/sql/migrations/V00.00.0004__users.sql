-- Users

CREATE TABLE users
(
  user_id BIGSERIAL,
  login TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL,
  salt INTEGER NOT NULL,
  user_role_id BIGINT NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (user_id),
  CONSTRAINT fk_user_roles FOREIGN KEY (user_role_id) REFERENCES user_roles (user_role_id)
);