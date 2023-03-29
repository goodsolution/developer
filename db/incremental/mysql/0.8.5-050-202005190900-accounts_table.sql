create table accounts(
    id int auto_increment,
    name varchar(200) not null,
    primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create unique index idx_name on accounts (name);
