create table crs_password_reset_tokens(
id int auto_increment,
customer_id int not null,
value varchar(300) not null,
expiration_datetime datetime not null,
used boolean not null,
primary key (id),
foreign key (customer_id) references crs_customers(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

CREATE UNIQUE INDEX unique_value ON crs_password_reset_tokens (value);