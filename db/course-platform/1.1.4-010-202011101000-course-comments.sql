create table crs_course_comments(
id int auto_increment,
course_id int not null,
customer_id int not null,
text varchar(5000) not null,
status varchar(1) not null,
create_datetime datetime not null,
delete_datetime datetime,
primary key (id),
foreign key (course_id) references crs_courses(id),
foreign key (customer_id) references crs_customers(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;