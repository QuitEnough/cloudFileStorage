CREATE SCHEMA IF NOT EXISTS cloudFileService

CREATE TABLE cloudFileService.usr(
    id PRIMARY KEY,
    user_name VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);