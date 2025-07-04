--liquibase formatted sql

--changeset isolomin:1
CREATE TABLE IF NOT EXISTS wallet (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    wallet_uuid UUID NOT NULL UNIQUE,
    balance DECIMAL(19, 2) NOT NULL
);

--changeset isolomin:2
CREATE INDEX idx_wallet_wallet_uuid ON wallet (wallet_uuid);

--changeset isolomin:3
INSERT INTO wallet (wallet_uuid, balance)
VALUES('3fa85f64-5717-4562-b3fc-2c963f66afa6',
       '1'
);