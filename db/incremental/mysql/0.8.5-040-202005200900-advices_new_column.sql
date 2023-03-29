alter table advices add start_date DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP;
alter table advices add stop_date DATETIME  NOT NULL DEFAULT '9999-12-31';
alter table advices add period VARCHAR(1)  NOT NULL DEFAULT 'I';
alter table advices add status VARCHAR(1)  NOT NULL DEFAULT 'A';