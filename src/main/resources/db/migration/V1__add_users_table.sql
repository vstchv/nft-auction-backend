CREATE TABLE IF NOT EXISTS users
(
    id                  SERIAL PRIMARY KEY,
    username            VARCHAR(255) NOT NULL,
    password            VARCHAR(255) NOT NULL,
    first_name          VARCHAR(255),
    last_name           VARCHAR(255),
    email               VARCHAR(255) NOT NULL,
    verified_profile BOOLEAN,
    role                VARCHAR(255),
    enabled             BOOLEAN      NOT NULL
);
