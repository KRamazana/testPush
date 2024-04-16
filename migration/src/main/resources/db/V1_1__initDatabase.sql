CREATE TABLE IF NOT EXISTS clients (
    id                 varchar(255) not null,
    points             int8,
    client_name        varchar(255) not null,
    client_role_id     int8;
);

CREATE TABLE IF NOT EXISTS roles (
    id                 int8 not null,
    client_role        varchar(255) unique;
);

ALTER TABLE clients
    ADD CONSTRAINT fk_client_role_id FOREIGN KEY (client_role_id) REFERENCES roles(id);