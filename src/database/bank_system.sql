DROP SCHEMA IF EXISTS bank_system CASCADE;
CREATE SCHEMA bank_system;
SET search_path = 'bank_system';
SET SCHEMA 'bank_system';

create table "user"
(
    type varchar check ( type in ('Clerk','Customer','Admin') ),
    username varchar,
    password varchar not null,
    PRIMARY KEY (username)
);

create table Account
(
    account_number VARCHAR(6)  CHECK (account_number ~ '^[0-9]{6}$'),
    balance decimal(12,2) not null,
    "owner" varchar references "user"(username) not null,
    PRIMARY KEY (account_number)
);

create table Transaction
(
    account_number varchar(6) references Account(account_number),
    username varchar references "user"(username),
    amount decimal(8,2) check ( amount > 0 ) not null ,
    date_time date,
    type varchar check (type in('Deposit, Withdraw')),
    PRIMARY KEY (account_number,username,date_time)
);

insert into "user"(type, username, password)
values ('Customer', 'ion_caus', '12345');
