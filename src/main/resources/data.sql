INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO restaurants (name)
VALUES ('macdonald''s'),
       ('Noma Copenhagen, Denmark'),
       ('Maison Premiere New York, US');

INSERT INTO MENU_ITEMS (RESTAURANT_ID,NAME)
VALUES (1,'Big Mac'),
       (1,'Iced Coffee'),
       (1,'Hash Browns'),
       (1,'Coca-Cola');

INSERT INTO VOTES (RESTAURANT_ID,USER_ID,DATE_TIME)
VALUES 
	(1,1,'2021-12-30 10:00:00');
