CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY NOT NULL,
    email    VARCHAR(200) UNIQUE   NOT NULL,
    password VARCHAR(200)          NOT NULL,
    role     VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS files
(
    id    BIGSERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(200)          NOT NULL
);

CREATE TABLE IF NOT EXISTS users_files
(
    user_id  BIGINT NOT NULL,
    files_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, files_id)
);