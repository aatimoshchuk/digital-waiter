SET search_path TO dw;

CREATE TYPE NOTIFICATION_STATUS AS ENUM (
    'NEW',
    'IN_PROGRESS',
    'ACKED',
    'EXPIRED'
);

CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    pos_terminal_group_id VARCHAR NOT NULL,
    text VARCHAR NOT NULL,
    status dw.NOTIFICATION_STATUS NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    pulled_at TIMESTAMP WITH TIME ZONE,
    pull_token VARCHAR
)