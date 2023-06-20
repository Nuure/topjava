DELETE
FROM user_role;
DELETE
FROM meals;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2023-06-01 08:00:00', 'Breakfast of user', 350),
       (100000, '2023-06-01 12:30:00', 'Lunch of user', 500),
       (100000, '2023-06-01 18:00:00', 'Dinner of user', 700),
       (100000, '2023-06-02 07:30:00', 'Breakfast of user', 1000),
       (100000, '2023-06-02 13:00:00', 'Lunch of user', 1000),
       (100000, '2023-06-02 19:30:00', 'Dinner of user', 800),
       (100001, '2023-06-01 09:30:00', 'Breakfast of admin', 400),
       (100001, '2023-06-01 13:00:00', 'Lunch of admin', 1000),
       (100001, '2023-06-01 19:00:00', 'Dinner of admin', 1000),
       (100001, '2023-06-02 08:00:00', 'Breakfast of admin', 350),
       (100001, '2023-06-02 12:30:00', 'Lunch of admin', 500),
       (100001, '2023-06-02 18:30:00', 'Dinner of admin', 700);