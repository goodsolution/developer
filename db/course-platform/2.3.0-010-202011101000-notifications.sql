create table crs_notifications(
    id int auto_increment,
    create_datetime datetime not null,
    seen_datetime datetime,
    delete_datetime datetime,
    customer_id int not null,
    title varchar(20) not null,
    content varchar(100) not null,
    link varchar(500) not null,
    status varchar(1) not null,
    type varchar(1) not null,
    kind varchar(1) not null,
    language varchar(2) not null,
    primary key (id),
    foreign key (customer_id) references crs_customers(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;


