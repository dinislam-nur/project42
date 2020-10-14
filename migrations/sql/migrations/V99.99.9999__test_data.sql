-- test data

INSERT INTO table_statuses (table_status_id, table_status_name)
VALUES (1, 'RESERVED'),
       (2, 'NOT_RESERVED');

INSERT INTO tables (number, table_status_id)
VALUES (1, 2);

INSERT INTO user_roles (user_role_id, role_name)
VALUES (1, 'STAFF'),
       (2, 'GUEST');

INSERT INTO users (login, password, salt, user_role_id)
VALUES ('admin', 'admin', 123, 2);

INSERT INTO food_categories (food_category_id, category_name)
VALUES (1, 'DRINK'),
       (2, 'HOT_DISHES'),
       (3, 'SOMETHING_ELSE');

INSERT INTO foods (food_name, price, picture, food_category_id)
VALUES ('compot', 1.0, 'test.ru', 1),
       ('borsh', 2.0, 'test.ru', 2);

INSERT INTO order_statuses (order_status_id, order_status_name)
VALUES (1, 'COLLECT'),
       (2, 'USER_CONFIRMED'),
       (3, 'STAFF_CONFIRMED'),
       (4, 'PREPARING'),
       (5, 'DONE');

INSERT INTO orders (order_time, user_id, is_payed, table_id, order_status_id)
VALUES (current_timestamp, 1, false, 1, 2);

INSERT INTO foods2order (order_id, food_id)
VALUES (1, 1),
       (1, 2);

INSERT INTO session_statuses (session_status_id, session_status_name)
VALUES (1, 'OPENED'),
       (2, 'CLOSED');

INSERT INTO sessions (session_status_id, token, timeout, table_id, user_id)
VALUES (1, 'some_token', current_timestamp, 1, 1);