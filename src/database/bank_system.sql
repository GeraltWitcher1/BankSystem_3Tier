DROP SCHEMA IF EXISTS bank_system CASCADE;
CREATE SCHEMA bank_system;
SET search_path = 'bank_system';
SET SCHEMA 'bank_system';

create table "user"
(
    cpr varchar(10) CHECK ( cpr ~ '^[0-9]{10}$' ),
    password varchar not null,
    type varchar check ( type in ('Clerk','Customer','Admin') ),
    PRIMARY KEY (cpr)
);

create table Account
(
    account_number int CHECK (account_number between 100000 and 999999),
    balance decimal(12,2) not null,
    "owner" varchar(10) not null references "user"(cpr) ON delete cascade,
    PRIMARY KEY (account_number)
);

create table Transaction
(
    transaction_id SERIAL,
    account_number int references Account(account_number) ON delete cascade,
    user_cpr varchar(10) not null references "user"(cpr) ON delete cascade,
    amount decimal(8,2) check ( amount > 0 ) not null ,
    date_time timestamp,
    type varchar check (type in('Deposit', 'Withdrawal')),
    PRIMARY KEY (transaction_id)
);

insert into "user"(cpr, password, type)
values ('1001102932', '123456', 'Customer');

insert into account(account_number, balance, owner)
values (114509, 900.2, '1001102932');
