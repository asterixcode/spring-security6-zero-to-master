CREATE SEQUENCE IF NOT EXISTS authorities_id_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS authorities
(
    id          BIGINT      NOT NULL DEFAULT nextval('authorities_id_seq'),
    customer_id BIGINT      NOT NULL,
    name        VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT authorities_customer_id_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE
);
