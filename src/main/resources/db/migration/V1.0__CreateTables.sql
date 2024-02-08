CREATE TABLE driver (
    driver_id uuid PRIMARY KEY,
    name varchar(255) NOT NULL,
    email varchar(320) NOT NULL,
    version integer
);
CREATE UNIQUE INDEX driver__email ON driver(email);

CREATE TABLE trip (
    trip_id uuid PRIMARY KEY,
    driver_id uuid NOT NULL,
    origin varchar(2000) NOT NULL,
    destination varchar(2000) NOT NULL,
    departure_time timestamp NOT NULL,
    update_time timestamp NOT NULL,
    version integer,

    CONSTRAINT fk_driver FOREIGN KEY(driver_id) REFERENCES driver(driver_id)
);
CREATE INDEX trip__driver_id ON trip(driver_id);

CREATE TABLE ride_offer (
    ride_offer_id uuid PRIMARY KEY,
    trip_id uuid NOT NULL,
    seats int NOT NULL,

    CONSTRAINT fk_trip FOREIGN KEY (trip_id) REFERENCES trip(trip_id)
);
CREATE INDEX ride_offer__trip_id ON ride_offer(trip_id);