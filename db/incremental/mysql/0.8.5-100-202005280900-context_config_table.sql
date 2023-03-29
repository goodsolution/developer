create table context_configs(
    id int auto_increment,
    application_id varchar(200),
    context varchar(200),
    name varchar(200),
    type varchar(200),
    value varchar(200),
    primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;
