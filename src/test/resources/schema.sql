CREATE TABLE IF NOT EXISTS roles
(
    id  BIGINT not null,
    name VARCHAR(255) not null,
    CONSTRAINT role_pk PRIMARY KEY (id)
);

create table if not exists users
(
    id         BIGINT not null,
    first_name VARCHAR(255) not null,
    last_name  VARCHAR(255) not null,
    email      VARCHAR(255) not null,
    password   VARCHAR(255) not null,
    role_id    BIGINT not null,
    CONSTRAINT user_id PRIMARY KEY (id),
    CONSTRAINT user_role_FK FOREIGN KEY (role_id) REFERENCES roles
);

create table if not exists flights
(
    id                   BIGINT not null,
    title                VARCHAR(255) not null,
    airport_of_departure VARCHAR(255) not null,
    airport_of_arrival   VARCHAR(255) not null,
    departure_time       DATETIME,
    arrived_time         DATETIME,
    flight_status        VARCHAR(255) not null,
    price                DECIMAL,
    user_id              BIGINT not null,
    CONSTRAINT flight_pk PRIMARY KEY (id),
    CONSTRAINT flight_user_fk FOREIGN KEY (user_id) REFERENCES users
);