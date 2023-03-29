create table crs_authors(
id int auto_increment,
first_name varchar(200) not null,
last_name varchar(200) not null,
primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create table crs_courses(
id int auto_increment,
title varchar(200) not null,
description varchar(5000),
image_name varchar(200),
price decimal(10,2) not null,
language varchar(20) not null,
primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create table crs_lessons(
id int auto_increment,
course_id int not null,
title varchar(200) not null,
description varchar(5000),
movie_link varchar(500),
primary key (id),
foreign key (course_id) references crs_courses(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create table crs_customers(
id int auto_increment,
login varchar(200) not null,
password_hash varchar(200) not null,
language varchar(20) not null,
primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create table crs_orders(
id int auto_increment,
number varchar(200) not null,
customer_id int not null,
purchase_date date not null,
total_price decimal(10,2) not null,
status varchar(100) not null,
primary key (id),
foreign key (customer_id) references crs_customers(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create table crs_purchased_courses(
id int auto_increment,
order_id int not null,
course_id int not null,
price decimal(10,2) not null,
primary key (id),
foreign key (order_id) references crs_orders(id),
foreign key (course_id) references crs_courses(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create table crs_invoices(
id int auto_increment,
order_id int not null,
type varchar(100) not null,
primary key (id),
foreign key (order_id) references crs_orders(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create table crs_email_templates(
id int auto_increment,
title varchar(200) not null,
content varchar(5000) not null,
language varchar(20) not null,
event varchar(200) not null unique,
primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

create table crs_sent_emails(
id int auto_increment,
order_id int,
customer_id int not null,
title varchar(200) not null,
content varchar(5000) not null,
language varchar(20) not null,
event varchar(200) not null unique,
primary key (id),
foreign key (order_id) references crs_orders(id),
foreign key (customer_id) references crs_customers(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;