CREATE TABLE IF NOT EXISTS clients (
    id                 varchar(255) not null,
    points             bigint,
    client_name        varchar(255) not null,
    client_role_id     bigint;
);

CREATE TABLE IF NOT EXISTS roles (
    id                 bigint not null,
    client_role        varchar(255) unique;
);

ALTER TABLE clients
    ADD CONSTRAINT fk_client_role_id FOREIGN KEY (client_role_id) REFERENCES roles(id);