CREATE TABLE notification_tasks (
    id BIGSERIAL PRIMARY KEY,
    trip_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    attempts INTEGER DEFAULT 0,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    last_attempt TIMESTAMP WITHOUT TIME ZONE,
    processed_by VARCHAR(255)
);