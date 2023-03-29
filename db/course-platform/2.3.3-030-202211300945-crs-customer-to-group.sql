create table crs_customer_to_group
(
    id                          int auto_increment,
    customer_id 		        int 		not null,
    customer_group_id			int			not null,
    foreign key	(customer_id) references crs_customers(id),
    foreign key (customer_group_id) references crs_customer_groups(id),
    primary key (id)
)
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;