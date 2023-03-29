drop table advices_log;
drop table advice_categories;
drop table advice_categories_used;
drop table advice_answer_options;
drop table triggered_advices;
drop table advices;
create table advices(
id int auto_increment,
app_id varchar(100) not null,
context varchar(100) not null,
content varchar(64519),
type varchar(1) not null,
title varchar(100),
variable_name varchar(100),
execution_condition varchar(1000),
combo_json varchar(100),
status varchar(1) not null,
primary key (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;