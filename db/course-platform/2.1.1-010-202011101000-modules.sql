create table crs_modules(
id int auto_increment,
course_id int not null,
name varchar(500) not null,
order_number int not null,
delete_datetime datetime,
primary key (id),
foreign key (course_id) references crs_courses(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

ALTER TABLE crs_lessons ADD module_id int;
ALTER TABLE crs_lessons ADD CONSTRAINT fkey_module_id FOREIGN KEY (module_id) REFERENCES crs_modules(id);