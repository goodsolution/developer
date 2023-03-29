create table crs_customer_authorities (
    id int auto_increment,
    customer_id int not null,
	authority varchar(50) not null,
	primary key (id),
    foreign key (customer_id) references crs_customers(id)
);
create unique index ix_auth on crs_customer_authorities (customer_id, authority);