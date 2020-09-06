DROP TABLE dishes IF EXISTS;
DROP TABLE menus IF EXISTS;
DROP TABLE votes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE global_seq AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id               INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    name             VARCHAR(255)            NOT NULL,
    email            VARCHAR(255)            NOT NULL,
    password         VARCHAR(255)            NOT NULL,
    enabled          BOOLEAN DEFAULT TRUE    NOT NULL,
    registered       TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    name    VARCHAR(255)         NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx on restaurants (name);

CREATE TABLE menus
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    restaurant_id INTEGER          NOT NULL,
    date          DATE DEFAULT NOW NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX menus_unique_restaurant_date_idx ON menus (restaurant_id, date);

CREATE TABLE dishes
(
    id          INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    menu_id     INTEGER      NOT NULL,
    name        VARCHAR(255) NOT NULL,
    price       DOUBLE       NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menus (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX dishes_unique_name_idx on dishes (name);

CREATE TABLE votes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
    user_id       INTEGER          NOT NULL,
    restaurant_id INTEGER          NOT NULL,
    date          DATE DEFAULT NOW NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX votes_unique_user_date_idx ON votes (user_id, date);