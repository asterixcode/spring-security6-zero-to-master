CREATE SEQUENCE IF NOT EXISTS customers_id_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS customers
(
    customer_id   BIGINT       NOT NULL DEFAULT nextval(customers_id_seq),
    name          VARCHAR(50)  NOT NULL,
    email         VARCHAR(45)  NOT NULL,
    mobile_number VARCHAR(20)  NOT NULL,
    password      VARCHAR(200) NOT NULL,
    role          VARCHAR(45)  NOT NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (customer_id),
    UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS accounts
(
    account_number BIGINT       NOT NULL,
    account_type   VARCHAR(100) NOT NULL,
    branch_address VARCHAR(200) NOT NULL,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    customer_id    BIGINT       NOT NULL,
    PRIMARY KEY (account_number),
    CONSTRAINT accounts_customer_id_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS account_transactions
(
    transaction_id      UUID         NOT NULL DEFAULT uuid_generate_v4(),
    account_number      BIGINT       NOT NULL,
    customer_id         BIGINT       NOT NULL,
    transaction_date    DATE         NOT NULL,
    transaction_summary VARCHAR(200) NOT NULL,
    transaction_type    VARCHAR(100) NOT NULL,
    transaction_amount  BIGINT       NOT NULL,
    closing_balance     BIGINT       NOT NULL,
    created_at          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (transaction_id),
    CONSTRAINT account_transactions_account_number_fk FOREIGN KEY (account_number) REFERENCES accounts (account_number) ON DELETE CASCADE,
    CONSTRAINT account_transactions_customer_id_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS loans_id_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS loans
(
    loan_number        BIGINT       NOT NULL DEFAULT nextval(loans_id_seq),
    customer_id        BIGINT       NOT NULL,
    start_date         DATE         NOT NULL,
    loan_type          VARCHAR(100) NOT NULL,
    total_loan         BIGINT       NOT NULL,
    amount_paid        BIGINT       NOT NULL,
    outstanding_amount BIGINT       NOT NULL,
    created_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (loan_number),
    CONSTRAINT loan_customer_id_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS cards_id_seq START 1 INCREMENT 1;
CREATE TABLE IF NOT EXISTS cards
(
    card_id          BIGINT       NOT NULL DEFAULT nextval(cards_id_seq),
    card_number      VARCHAR(100) NOT NULL,
    customer_id      BIGINT       NOT NULL,
    card_type        VARCHAR(100) NOT NULL,
    total_limit      BIGINT       NOT NULL,
    amount_used      BIGINT       NOT NULL,
    available_amount BIGINT       NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (card_id),
    CONSTRAINT cards_customer_id_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id) ON DELETE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS notice_details_id_seq START 1 INCREMENT 1;
CREATE TABLE notice_details
(
    notice_id       BIGINT       NOT NULL DEFAULT nextval(notice_details_id_seq),
    notice_summary  VARCHAR(200) NOT NULL,
    notice_details  VARCHAR(500) NOT NULL,
    notice_beg_date DATE         NOT NULL,
    notice_end_date DATE                  DEFAULT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (notice_id)
);

CREATE TABLE contact_messages
(
    contact_id    VARCHAR(50)   NOT NULL,
    contact_name  VARCHAR(50)   NOT NULL,
    contact_email VARCHAR(100)  NOT NULL,
    subject       VARCHAR(500)  NOT NULL,
    message       VARCHAR(2000) NOT NULL,
    created_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (contact_id)
);
