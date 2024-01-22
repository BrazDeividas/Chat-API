CREATE DATABASE chatdb;
CREATE TYPE role_name AS ENUM ('ROLE_ADMIN', 'ROLE_USER');
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role role_name NOT NULL,
    created_at TIMESTAMP NOT NULL
);
CREATE TABLE messages (
    message_id SERIAL PRIMARY KEY,
    content TEXT,
    sender_id INT REFERENCES users(user_id),
    created_at TIMESTAMP NOT NULL
);


