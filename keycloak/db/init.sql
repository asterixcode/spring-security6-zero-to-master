-- Script to initialize DB from docker-compose

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'keycloak_db') THEN
        -- Create the database
        CREATE DATABASE "keycloak_db";
    END IF;
END $$;

\c "keycloak_db";