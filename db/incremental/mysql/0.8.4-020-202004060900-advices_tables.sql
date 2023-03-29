create table advices(
id int auto_increment,
app_id varchar(100),
domain varchar(100),
content varchar(64519),
type varchar(1),
scope varchar(100),
action varchar(500),
data_type varchar(100),
component varchar(100),
primary key (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

create table triggered_advices(
id int auto_increment,
advice_id int,
app_id varchar(100),
domain varchar(100),
domain_id varchar(500),
content varchar(63995),
type varchar(1),
lang varchar(10),
scope varchar(100),
action varchar(500),
data_type varchar(100),
component varchar(100),
score int,
status varchar(1),
primary key (id),
foreign key (advice_id) references advices (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

alter table triggered_advices add trigger_datetime DATETIME NOT NULL;
alter table advices add class VARCHAR(500);

