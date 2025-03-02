CREATE SEQUENCE customers_id_seq START 1 INCREMENT 50;

CREATE TABLE IF NOT EXISTS public.customers
(
    id       BIGINT DEFAULT nextval('customers_id_seq') NOT NULL,
    email    VARCHAR(45)                                NOT NULL,
    password VARCHAR(200)                               NOT NULL,
    role     VARCHAR(45)                                NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);