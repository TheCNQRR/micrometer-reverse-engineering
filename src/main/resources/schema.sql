CREATE TABLE users (
                       id           UUID         PRIMARY KEY,
                       username     VARCHAR(255) NOT NULL UNIQUE,
                       display_name VARCHAR(255) NOT NULL
);