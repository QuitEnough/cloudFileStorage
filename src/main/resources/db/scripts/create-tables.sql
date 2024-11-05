CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY NOT NULL,
    email    VARCHAR(200) UNIQUE   NOT NULL,
    password VARCHAR(200)          NOT NULL,
    role     VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS directories
(
    id    BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(200)          NOT NULL,
    user_id BIGSERIAL REFERENCES users(id),
    parent_id BIGSERIAL REFERENCES directories(id),
    UNIQUE (id, user_id, parent_id)
);

CREATE TABLE IF NOT EXISTS files
(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(200) NOT NULL,
    extension VARCHAR(200) NOT NULL,
    uuid  UUID DEFAULT uuid_generate_v4() NOT NULL,
    directory_id BIGINT REFERENCES directories(id),
    UNIQUE (name, extension)
);


--     user_id BIGSERIAL FOREIGN KEY NOT NULL
-- parent_id (id папки сверху - запись в той же таблице. если в корне - null)
-- constraint (parent_id, user_id, directory_id) unique
-- check the null directory (root)

-- create table files (
--     id , directory_id (ссылка на директорию, если в корне - null), user_id, name (только имя файла), extension (расширение .png), UUID (on name without extension)
--     constraint (name, extension) -- unique
-- )