CREATE TABLE users(
    id SERIAL PRIMARY KEY NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL
);