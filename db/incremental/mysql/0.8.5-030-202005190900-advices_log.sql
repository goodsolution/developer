create table advices_log (
id int auto_increment,
advice_id int NOT NULL,
triggered_advice_id int,
app_id varchar(100) NOT NULL,
domain varchar(100) NOT NULL,
message varchar(63995) NOT NULL,
date_time DATETIME NOT NULL,
primary key (id),
foreign key (advice_id) references advices (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;