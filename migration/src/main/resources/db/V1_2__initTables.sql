INSERT INTO clients(clientId, points, name)
VALUES (1, 100, 'Richard');

INSERT INTO clients(clientId, point, name)
VALUES (2, 60, 'Erik');

INSERT INTO roles(id, client_role)
VALUES (1, 'user');

INSERT INTO roles(id, client_role)
VALUES (2, 'super');

INSERT INTO application_roles(role_id, client_id)
VALUES (1, 1);

INSERT INTO application_roles(role_id, client_id)
VALUES (2, 2);


