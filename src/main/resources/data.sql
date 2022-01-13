INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANTS (name)
VALUES ('McDonald''s'),
       ('Noma Copenhagen, Denmark'),
       ('Maison Premiere New York, US');

INSERT INTO MENU_ITEMS (restaurant_id, name, date,price)
VALUES (1, 'Big Mac', '2022-01-01',200),
       (1, 'Iced Coffee', '2022-01-01',100),
       (1, 'Hash Browns', '2022-01-01',150),
       (1, 'Coca-Cola', '2022-01-01',50);

INSERT INTO VOTES (restaurant_id, user_id, date)
VALUES (1, 1, '2021-01-01');
