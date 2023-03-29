create table triggered_advices(
id int auto_increment,
advice_id int not null,
customer_id int not null,
app_id varchar(100),
context varchar(100),
context_id varchar(500),
title varchar(500),
content varchar(63995),
type varchar(1),
lang varchar(10),
data_type varchar(100),
status varchar(1) NOT NULL,
variable_name varchar(100),
execution_condition varchar(1000),
combo_json varchar(100),
create_datetime DATETIME NOT NULL,
trigger_datetime DATETIME,
primary key (id),
foreign key (advice_id) references advices (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

create table advice_categories(
    id int auto_increment,
    application_id varchar(200) NOT NULL,
    context varchar(200) NOT NULL,
    name varchar(200) NOT NULL,
    description varchar(1000),
    primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create table advice_categories_used (
    id int auto_increment,
    category_id int NOT NULL,
    customer_id int NOT NULL,
    foreign key (category_id) references advice_categories (id),
    foreign key (customer_id) references crs_customers (id),
    primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

alter table advices add category_id int;
alter table advices add response_condition varchar(1000);
alter table advices add response_true_content varchar(63995);
alter table advices add response_else_content varchar(63995);
alter table context_vars add customer_id int;