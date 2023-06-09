CREATE SCHEMA IF NOT EXISTS app;

CREATE TABLE IF NOT EXISTS app.report(
    uuid UUID NOT NULL,
    creation_time TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    last_updated TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    status VARCHAR(15) NOT NULL,
    report_type VARCHAR(20) NOT NULL,
    description TEXT NOT NULL,
    user_id UUID NOT NULL,
    start_time DATE NOT NULL,
    end_time DATE NOT NULL,
    CONSTRAINT pk_report_id PRIMARY KEY (uuid)
)