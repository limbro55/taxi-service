CREATE TABLE drivers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL UNIQUE,  -- ВОТ ЭТО ДОБАВИЛИ
    car_model VARCHAR(255),
    car_number VARCHAR(50),
    rating DOUBLE PRECISION DEFAULT 5.0,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);