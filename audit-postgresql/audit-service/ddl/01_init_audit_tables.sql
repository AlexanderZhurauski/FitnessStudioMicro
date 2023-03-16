CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE IF NOT EXISTS app.audit_entity(
    uuid UUID NOT NULL,
    creation_time TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    user_id UUID NOT NULL,
    mail VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    text TEXT NOT NULL,
    type VARCHAR(30) NOT NULL,
    entity_id VARCHAR(36) NOT NULL,
    CONSTRAINT pk_audit_id PRIMARY KEY (uuid)
);