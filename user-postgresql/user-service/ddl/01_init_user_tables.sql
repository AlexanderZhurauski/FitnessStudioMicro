CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE IF NOT EXISTS app.user_role (
    id SERIAL,
    role VARCHAR(20),
    CONSTRAINT pk_user_role_id PRIMARY KEY (id),
    CONSTRAINT user_role_enum CHECK (role IN ('ADMIN', 'USER'))
);

CREATE TABLE IF NOT EXISTS app.user_status (
    id SERIAL,
    status VARCHAR(20),
    CONSTRAINT pk_user_status_id PRIMARY KEY (id),
    CONSTRAINT user_status_enum CHECK (status
        IN ('ACTIVATED', 'DEACTIVATED', 'WAITING_ACTIVATION'))
);

CREATE TABLE IF NOT EXISTS app.user (
    id UUID,
    creation_time TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    last_updated TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    mail TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    full_name TEXT NOT NULL,
    role_id INT NOT NULL,
    status_id INT NOT NULL,
    CONSTRAINT pk_user_id PRIMARY KEY (id),
    CONSTRAINT fk_role_id FOREIGN KEY
        (role_id) REFERENCES app.user_role (id),
    CONSTRAINT fk_status_id FOREIGN KEY
        (status_id) REFERENCES app.user_status (id)
);
