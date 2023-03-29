create table crs_course_attachments(
id int auto_increment,
course_id int not null,
name varchar(300) not null,
file_name varchar(500) not null,
delete_datetime datetime,
primary key (id),
foreign key (course_id) references crs_courses(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;