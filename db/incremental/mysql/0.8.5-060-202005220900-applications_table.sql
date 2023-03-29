create table applications(
    id int auto_increment,
    application_id varchar(200) not null,
    description varchar(5000),
    primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create unique index idx_application_id on applications (application_id);
