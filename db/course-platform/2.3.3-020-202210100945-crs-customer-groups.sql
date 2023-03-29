create table crs_customer_groups
(
    id                  int auto_increment,
    create_datetime     datetime      not null,
    delete_datetime     datetime,
    name               varchar(200)  not null,
    primary key (id)
)
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

create table crs_assigned_customer_groups
(
    id                  int auto_increment,
    customer_id         int not null,
    customer_group_id   int not null,
    create_datetime     datetime      not null,
    delete_datetime     datetime,
    primary key (id),
    foreign key(customer_id) references crs_customers(id),
    foreign key(customer_group_id) references crs_customer_groups(id)
)
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
    ;