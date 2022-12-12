INSERT INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'USER');

INSERT INTO users (id, first_name, last_name, email, password, role_id) VALUES (5, 'Nick', 'Green', 'nick@mail.com', '$2a$10$CJgEoobU2gm0euD4ygru4ukBf9g8fYnPrMvYk.q0GMfOcIDtUhEwC', 1);
INSERT INTO users (id, first_name, last_name, email, password, role_id) VALUES (6, 'Nora', 'White', 'nora@mail.com', '$2a$10$yYQaJrHzjOgD5wWCyelp0e1Yv1KEKeqUlYfLZQ1OQvyUrnEcX/rOy', 2);
INSERT INTO users (id, first_name, last_name, email, password, role_id) VALUES (4, 'Mike', 'Brown', 'mike@mail.com', '$2a$10$CdEJ2PKXgUCIwU4pDQWICuiPjxb1lysoX7jrN.Y4MTMoY9pjfPALO', 1);

INSERT INTO flights(id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id) VALUES (12, 'SAR45', 'Kyiv', 'Warsaw', '2020-09-16 14:00', '2020-09-17 14:00', 'ACTIVE', 5620, 4);
INSERT INTO flights(id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id) VALUES (13, 'FET75', 'Kyiv', 'Hamburg', '2020-09-16 14:14', '2020-09-16 18:14', 'ACTIVE', 3654, 4);
INSERT INTO flights(id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id) VALUES (14, 'SARS3', 'Warsaw', 'Berlin-Brandenburg', '2020-09-16 14:15', '2020-09-16 23:14', 'ACTIVE', 4789, 4);
INSERT INTO flights(id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id) VALUES (15, 'TIGR2', 'Dnipro', 'Iowa', '2020-09-16 14:00', '2020-09-17 14:14', 'ACTIVE', 14566, 4);
INSERT INTO flights(id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id) VALUES (16, 'DNE1', 'Warsaw', 'Lviv', '2020-09-16 14:14', '2020-09-16 22:59', 'ACTIVE', 7514, 4);
INSERT INTO flights(id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id) VALUES (17, 'EDB789', 'Hamburg', 'Iowa', '2020-09-16 14:15', '2020-09-16 17:15', 'ACTIVE', 9862, 4);
INSERT INTO flights(id, title, airport_of_departure, airport_of_arrival, departure_time, arrived_time, flight_status, price, owner_id) VALUES (18, 'FLI4736', 'Lviv', 'Berlin-Brandenburg', '2020-09-16 14:15', '2020-09-17 9:25','ACTIVE', 2354, 4);
