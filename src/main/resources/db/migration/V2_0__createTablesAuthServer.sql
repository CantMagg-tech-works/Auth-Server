SET search_path TO auth_server;

CREATE TABLE IF NOT EXISTS ec_user (
    user_id       SERIAL PRIMARY KEY,
    user_role_id INTEGER       NOT NULL,
    username      VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    creation_date DATE         NOT NULL,
    deleted_at    DATE
);

CREATE TABLE IF NOT EXISTS user_role (
    user_role_id     SERIAL PRIMARY KEY,
    role_description VARCHAR(100) NOT NULL
);


INSERT INTO user_role (role_description)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

ALTER TABLE ec_user
    ADD CONSTRAINT fk_user_user_role FOREIGN KEY (user_role_id) REFERENCES user_role (user_role_id);
