
INSERT INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'USER');

INSERT INTO users (id, first_name, last_name, email, password, role_id) VALUES (5, 'Nicky', 'Greeny', 'nicky@mail.com', '$2a$10$CJgEoobU2gm0euD4ygru4ukBf9g8fYnPrMvYk.q0GMfOcIDtUhEwC', 1);

INSERT INTO flights(id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id) VALUES (10, 'SAR45', 'Mexico', 'Warsaw', '2020-09-16 14:00', '2020-09-17 14:00', 'ACTIVE', 7620, 5);
INSERT INTO flights(id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id) VALUES (11, 'FET75', 'Mexico', 'Hamburg', '2020-09-16 14:14', '2020-09-16 18:14', 'ACTIVE', 8954, 5);
