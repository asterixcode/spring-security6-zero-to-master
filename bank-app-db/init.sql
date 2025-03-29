-- Script to initialize DB from docker-compose

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'bank-db') THEN
        -- Create the database
        CREATE DATABASE "bank-db";
    END IF;
END $$;

\c "bank-db";