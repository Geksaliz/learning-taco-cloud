CREATE TABLE IF NOT EXISTS ingredient (
    id   VARCHAR(4) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(7) NOT NULL CHECK (type IN ('WRAP', 'PROTEIN', 'VEGGIES', 'CHEESE', 'SAUCE'))
);

CREATE TABLE IF NOT EXISTS taco (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS ingredient_ref (
    id          VARCHAR(100) PRIMARY KEY,
    ingredient  VARCHAR(100) NOT NULL,
    taco        BIGINT,
    taco_key    INTEGER,

    CONSTRAINT fk_ingredient_ref_taco
        FOREIGN KEY (taco)
        REFERENCES taco(id)
        ON DELETE CASCADE
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users (
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username      VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    full_name     VARCHAR(255) NOT NULL,
    street        VARCHAR(255) NOT NULL,
    city          VARCHAR(255) NOT NULL,
    state         VARCHAR(255) NOT NULL,
    zip           VARCHAR(20)  NOT NULL,
    phone_number  VARCHAR(50)  NOT NULL
);

CREATE TABLE IF NOT EXISTS taco_order (
    id              BIGSERIAL PRIMARY KEY,
    placed_at       TIMESTAMP NOT NULL,
    delivery_name   VARCHAR(255) NOT NULL,
    delivery_street VARCHAR(255) NOT NULL,
    delivery_city   VARCHAR(255) NOT NULL,
    delivery_state  VARCHAR(255) NOT NULL,
    delivery_zip    VARCHAR(20)  NOT NULL,

    cc_number       VARCHAR(30),
    cc_expiration   VARCHAR(10),
    cc_cw           VARCHAR(10),

    user_id UUID,

    CONSTRAINT fk_taco_order_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);