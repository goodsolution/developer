alter table advice_categories_used add account_id int;
alter table advice_categories_used add external_transaction_id varchar(1000);
alter table advice_categories_used add status varchar(1) NOT NULL DEFAULT 'S' ;