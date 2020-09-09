DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM users;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@gmail.com', '{noop}user'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', (SELECT ID FROM USERS WHERE NAME = 'User')),
       ('ADMIN', (SELECT ID FROM USERS WHERE NAME = 'Admin')),
       ('USER', (SELECT ID FROM USERS WHERE NAME = 'Admin'));

INSERT INTO restaurants(name)
VALUES ('Restaurant 1'),
       ('Restaurant 2'),
       ('Restaurant 3');

INSERT INTO menus(restaurant_id, date)
VALUES ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1'), '2020-07-03'),
       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1'), '2020-07-02'),
       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1'), now()),
       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 2'), '2020-07-03'),
       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 2'), '2020-07-02'),
       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 2'), now()),
       ((SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 3'), '2020-07-03');

INSERT INTO votes (user_id, restaurant_id, date)
VALUES ((SELECT USERS.ID FROM USERS WHERE NAME = 'User'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1'), '2020-07-03'),
       ((SELECT USERS.ID FROM USERS WHERE NAME = 'Admin'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1'), '2020-07-03'),
       ((SELECT USERS.ID FROM USERS WHERE NAME = 'User'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1'), '2020-07-02'),
       ((SELECT USERS.ID FROM USERS WHERE NAME = 'Admin'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 2'), '2020-07-02'),
       ((SELECT USERS.ID FROM USERS WHERE NAME = 'User'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1'), now()),
       ((SELECT USERS.ID FROM USERS WHERE NAME = 'Admin'),
        (SELECT ID FROM RESTAURANTS WHERE NAME = 'Restaurant 2'), '2020-12-31');

-- dish:
-- Restaurant 1
INSERT INTO dishes (menu_id,name,price)
VALUES ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1')
           AND m.DATE = '2020-07-03'), 'dish 1', 25059),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1')
           AND m.DATE = '2020-07-03'), 'dish 2', 30033),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1')
           AND m.DATE = '2020-07-02'), 'dish 3', 15048),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1')
           AND m.DATE = '2020-07-02'), 'dish 4', 10099),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 1')
           AND m.DATE = current_date), 'dish 5', 20016);
-- dish
-- Restaurant 2
INSERT INTO dishes (menu_id,name,price)
VALUES ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 2')
           AND m.DATE = '2020-07-03'), 'dish 6', 5033),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 2')
           AND m.DATE = '2020-07-03'), 'dish 7', 10044),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 2')
           AND m.DATE = '2020-07-02'), 'dish 8', 20055),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 2')
           AND m.DATE = '2020-07-02'), 'dish 9', 30066),
       ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 2')
           AND m.DATE = current_date), 'dish 10', 40077);
-- dish
-- Restaurant 3
INSERT INTO dishes (menu_id,name,price)
VALUES ((SELECT ID
         FROM MENUS m
         WHERE m.RESTAURANT_ID =
               (SELECT RESTAURANTS.ID FROM RESTAURANTS WHERE NAME = 'Restaurant 3')
           AND m.DATE = '2020-07-03'), 'dish 11', 5088);