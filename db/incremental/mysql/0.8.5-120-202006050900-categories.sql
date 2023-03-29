create table advice_categories(
    id int auto_increment,
    application_id varchar(200) NOT NULL,
    context varchar(200) NOT NULL,
    name varchar(200) NOT NULL,
    description varchar(1000),
    tags varchar(2000),
    price DECIMAL(14, 2),
    currency_code VARCHAR(8) DEFAULT 'PLN' NOT NULL,
    period_code VARCHAR(8),
    primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

alter table advices add category_id int;

create table advice_categories_used (
    id int auto_increment,
    application_id varchar(200) NOT NULL,
    context varchar(200) NOT NULL,
    context_id varchar(1000),
    date_from DATE,
    date_to DATE,
    price DECIMAL(14, 2),
    currency_code VARCHAR(8) NOT NULL,
    category_id int NOT NULL,
    primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;