create table crs_action_trace(
id int auto_increment,
what varchar(200) not null,
value varchar(1000),
who varchar(1000),
creation_datetime datetime not null,
primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;