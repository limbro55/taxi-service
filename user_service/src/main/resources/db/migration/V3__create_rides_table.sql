CREATE TABLE rides (
    id BIGSERIAL PRIMARY KEY,
    passenger_id BIGINT NOT NULL,
    driver_id BIGINT,
    start_point VARCHAR(255) NOT NULL,
    end_point VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    fare DECIMAL(10, 2),
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_passenger FOREIGN KEY (passenger_id) REFERENCES passengers(id),
    CONSTRAINT fk_driver FOREIGN KEY (driver_id) REFERENCES drivers(id)
);