create table crs_customer_lesson_details(
id int auto_increment,
order_id int not null,
course_id int not null,
customer_id int not null,
lesson_id int not null,
watched boolean not null,
primary key (id),
foreign key (order_id) references crs_orders(id),
foreign key (course_id) references crs_courses(id),
foreign key (customer_id) references crs_customers(id),
foreign key (lesson_id) references crs_lessons(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;