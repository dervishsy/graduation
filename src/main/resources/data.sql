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
VALUES (1, 'Big Mac', current_date,200.20),
       (1, 'Iced Coffee', current_date,100),
       (1, 'Hash Browns', current_date,150),
       (1, 'Coca-Cola', current_date,50);

INSERT INTO VOTES (restaurant_id, user_id, date)
VALUES (1, 1, current_date);
