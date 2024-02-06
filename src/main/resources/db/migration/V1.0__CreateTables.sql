CREATE TABLE trip (
    trip_id uuid PRIMARY KEY,
    origin varchar(2000),
    destination varchar(2000),
    departure_time timestamp,
    driver uuid,
    update_time timestamp,
    version integer
);