create table suggestions(
    id int auto_increment,
    application_id varchar(200) NOT NULL,
    domain varchar(200) NOT NULL,
    domain_id varchar(500) NOT NULL,
    suggestion varchar(1000),
    primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

alter table accounts add type varchar(1) DEFAULT 'L';