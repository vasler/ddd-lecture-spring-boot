CREATE TABLE driver (
    driver_id uuid PRIMARY KEY,
    name varchar(255) NOT NULL,
    email varchar(320) NOT NULL,
    version integer
);
CREATE UNIQUE INDEX driver__email ON driver(email);

CREATE TABLE rider (
    rider_id uuid PRIMARY KEY,
    name varchar(255) NOT NULL,
    email varchar(320) NOT NULL,
    update_time timestamp NOT NULL,
    version integer
);
CREATE UNIQUE INDEX rider__email ON rider(email);

CREATE TABLE trip (
    trip_id uuid PRIMARY KEY,
    request_id varchar(255) NOT NULL,
    driver_id uuid NOT NULL,
    origin varchar(2000) NOT NULL,
    destination varchar(2000) NOT NULL,
    departure_time timestamp NOT NULL,
    arrival_time timestamp NOT NULL,
    was_ride_offered boolean NOT NULL,
    offered_seat_count integer NOT NULL,
    available_seat_count integer NOT NULL,
    update_time timestamp NOT NULL,
    version integer,

    CONSTRAINT fk_driver FOREIGN KEY(driver_id) REFERENCES driver(driver_id)
);
CREATE INDEX trip__driver_id ON trip(driver_id);
CREATE UNIQUE INDEX trip__driver_id__request_id ON trip(driver_id, request_id);

CREATE TABLE trip_ride_reservation (
    trip_ride_reservation_id uuid PRIMARY KEY,
    trip_id uuid NOT NULL,
    rider_id uuid NOT NULL,
    seat_count int NOT NULL,

    CONSTRAINT fk_trip FOREIGN KEY (trip_id) REFERENCES trip(trip_id),
    CONSTRAINT fk_rider FOREIGN KEY(rider_id) REFERENCES rider(rider_id)
);
CREATE INDEX trip_ride_reservation__trip_id ON trip_ride_reservation(trip_id);
CREATE INDEX trip_ride_reservation__rider_id ON trip_ride_reservation(rider_id);

CREATE TABLE rider_ride_reservation (
    rider_ride_reservation_id uuid PRIMARY KEY,
    rider_id uuid NOT NULL,
    trip_id uuid NOT NULL,
    departure_time timestamp NOT NULL,
    arrival_time timestamp NOT NULL,

    CONSTRAINT fk_rider FOREIGN KEY(rider_id) REFERENCES rider(rider_id),
    CONSTRAINT fk_trip FOREIGN KEY (trip_id) REFERENCES trip(trip_id)
);
CREATE INDEX rider_ride_reservation__rider_id ON rider_ride_reservation(rider_id);
CREATE INDEX rider_ride_reservation__trip_id ON rider_ride_reservation(trip_id);