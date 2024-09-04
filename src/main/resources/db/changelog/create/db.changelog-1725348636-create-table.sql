CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS PUBLIC.Users(
        id UUID NOT NULL DEFAULT UUID_GENERATE_V4(),
        created_at TIMESTAMP WITHOUT TIME ZONE,
        updated_at TIMESTAMP WITHOUT TIME ZONE,
        event_id UUID,
        first_name VARCHAR NOT NULL,
        last_name VARCHAR NOT NULL,
        user_email VARCHAR NOT NULL,
        is_active BOOLEAN DEFAULT true,
        email_received_at TIMESTAMP WITHOUT TIME ZONE,
        CONSTRAINT user_id_PK PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS PUBLIC.Event(
        id UUID NOT NULL DEFAULT UUID_GENERATE_V4(),
        created_at TIMESTAMP WITHOUT TIME ZONE,
        updated_at TIMESTAMP WITHOUT TIME ZONE,
        event_name VARCHAR,
        event_location VARCHAR,
        event_start_date TIMESTAMP WITHOUT TIME ZONE,
        event_end_date TIMESTAMP WITHOUT TIME ZONE,
        CONSTRAINT event_id_PK PRIMARY KEY(ID)
);