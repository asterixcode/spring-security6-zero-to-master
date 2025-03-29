INSERT INTO customers
    (name, email, mobile_number, password, role, created_at)
VALUES ('John', 'admin@mail.com', '51509080',
        '{bcrypt}$2a$12$HSY09zKcHraifsMFnUrKyet5aV/q5SHP6iUqXawByXfSpNnVinsu6',
        'admin', CURRENT_TIMESTAMP);


INSERT INTO accounts
(customer_id,
 account_number,
 account_type, branch_address, created_at)
VALUES (1,
        1865764534,
        'Savings',
        '123 Main Street, New York',
        CURRENT_TIMESTAMP);


INSERT INTO account_transactions
(transaction_id, account_number, customer_id,
 transaction_date, transaction_summary,
 transaction_type, transaction_amount,
 closing_balance, created_at)
VALUES (uuid_generate_v4(),
        1865764534,
        1,
        CURRENT_DATE - INTERVAL '7 days',
        'Coffee Shop',
        'Withdrawal',
        30, 34500,
        CURRENT_DATE - INTERVAL '7 days');

INSERT INTO account_transactions
(transaction_id, account_number, customer_id,
 transaction_date, transaction_summary,
 transaction_type, transaction_amount,
 closing_balance, created_at)
VALUES (uuid_generate_v4(),
        1865764534,
        1,
        CURRENT_DATE - INTERVAL '6 days',
        'Uber',
        'Withdrawal',
        100, 34400,
        CURRENT_DATE - INTERVAL '6 days');

INSERT INTO account_transactions
(transaction_id, account_number, customer_id,
 transaction_date, transaction_summary,
 transaction_type, transaction_amount,
 closing_balance, created_at)
VALUES (uuid_generate_v4(),
        1865764534,
        1,
        CURRENT_DATE - INTERVAL '5 days',
        'Self Deposit',
        'Deposit',
        500, 34900,
        CURRENT_DATE - INTERVAL '5 days');

INSERT INTO account_transactions
(transaction_id, account_number, customer_id,
 transaction_date, transaction_summary,
 transaction_type, transaction_amount,
 closing_balance, created_at)
VALUES (uuid_generate_v4(),
        1865764534,
        1,
        CURRENT_DATE - INTERVAL '4 days',
        'Ebay',
        'Withdrawal',
        600, 34300,
        CURRENT_DATE - INTERVAL '4 days');

INSERT INTO account_transactions
(transaction_id, account_number, customer_id,
 transaction_date, transaction_summary,
 transaction_type, transaction_amount,
 closing_balance, created_at)
VALUES (uuid_generate_v4(),
        1865764534,
        1,
        CURRENT_DATE - INTERVAL '2 days',
        'OnlineTransfer',
        'Deposit',
        700, 35000,
        CURRENT_DATE - INTERVAL '2 days');

INSERT INTO account_transactions
(transaction_id, account_number, customer_id,
 transaction_date, transaction_summary,
 transaction_type, transaction_amount,
 closing_balance, created_at)
VALUES (uuid_generate_v4(),
        1865764534,
        1,
        CURRENT_DATE - INTERVAL '1 days',
        'Amazon.com',
        'Withdrawal',
        100, 34900,
        CURRENT_DATE - INTERVAL '1 days');


INSERT INTO loans
(customer_id, start_date, loan_type,
 total_loan, amount_paid, outstanding_amount, created_at)
VALUES (1,
        '2020-10-13',
        'Home',
        200000, 50000, 150000,
        '2020-10-13');

INSERT INTO loans
(customer_id, start_date, loan_type,
 total_loan, amount_paid, outstanding_amount, created_at)
VALUES (1,
        '2020-06-06',
        'Vehicle', 40000, 10000, 30000,
        '2020-06-06');

INSERT INTO loans
(customer_id, start_date, loan_type, total_loan, amount_paid, outstanding_amount, created_at)
VALUES (1,
        '2018-02-14',
        'Home',
        50000, 10000, 40000,
        '2018-02-14');

INSERT INTO loans
(customer_id, start_date, loan_type, total_loan, amount_paid, outstanding_amount, created_at)
VALUES (1,
        '2018-02-14',
        'Personal',
        10000,
        3500,
        6500,
        '2018-02-14');


INSERT INTO cards
(card_number, customer_id, card_type, total_limit, amount_used, available_amount, created_at)
VALUES ('4565XXXX4656',
        1,
        'Credit',
        10000,
        500,
        9500,
        CURRENT_TIMESTAMP);


INSERT INTO cards
(card_number, customer_id, card_type, total_limit, amount_used, available_amount, created_at)
VALUES ('3455XXXX8673',
        1,
        'Credit',
        7500,
        600,
        6900,
        CURRENT_TIMESTAMP);

INSERT INTO cards
(card_number, customer_id, card_type, total_limit, amount_used, available_amount, created_at)
VALUES ('2359XXXX9346',
        1,
        'Credit',
        20000,
        4000,
        16000,
        CURRENT_TIMESTAMP);


INSERT INTO notice_details
(notice_summary, notice_details, notice_beg_date, notice_end_date, created_at, updated_at)
VALUES ('Home Loan Interest rates reduced',
        'Home loan interest rates are reduced as per the goverment guidelines. The updated rates will be effective immediately',
        CURRENT_DATE - INTERVAL '30 days',
        CURRENT_DATE + INTERVAL '30 days',
        CURRENT_DATE,
        CURRENT_DATE);

INSERT INTO notice_details
(notice_summary, notice_details, notice_beg_date, notice_end_date, created_at, updated_at)
VALUES ('Net Banking Offers',
        'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher',
        CURRENT_DATE - INTERVAL '30 days',
        CURRENT_DATE + INTERVAL '30 days',
        CURRENT_DATE,
        CURRENT_DATE);

INSERT INTO notice_details
(notice_summary, notice_details, notice_beg_date, notice_end_date, created_at, updated_at)
VALUES ('Mobile App Downtime',
        'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities',
        CURRENT_DATE - INTERVAL '30 days',
        CURRENT_DATE + INTERVAL '30 days',
        CURRENT_DATE,
        CURRENT_DATE);

INSERT INTO notice_details
(notice_summary, notice_details, notice_beg_date, notice_end_date, created_at, updated_at)
VALUES ('E Auction notice',
        'There will be a e-auction on 12/08/2020 on the Bank website for all the stubborn arrears.Interested parties can participate in the e-auction',
        CURRENT_DATE - INTERVAL '30 days',
        CURRENT_DATE + INTERVAL '30 days',
        CURRENT_DATE,
        CURRENT_DATE);

INSERT INTO notice_details
(notice_summary, notice_details, notice_beg_date, notice_end_date, created_at, updated_at)
VALUES ('Launch of Millennia Cards',
        'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase',
        CURRENT_DATE - INTERVAL '30 days',
        CURRENT_DATE + INTERVAL '30 days',
        CURRENT_DATE,
        CURRENT_DATE);

INSERT INTO notice_details
(notice_summary, notice_details, notice_beg_date, notice_end_date, created_at, updated_at)
VALUES ('COVID-19 Insurance',
        'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details',
        CURRENT_DATE - INTERVAL '30 days',
        CURRENT_DATE + INTERVAL '30 days',
        CURRENT_DATE,
        CURRENT_DATE);