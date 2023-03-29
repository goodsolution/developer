create table crs_files(
id int auto_increment,
name varchar(300) not null,
original_name varchar(5000) not null,
content_type varchar(500) not null,
size_in_bytes int not null,
primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;

CREATE UNIQUE INDEX name_unique ON crs_files(name);

create table crs_lesson_attachments(
id int auto_increment,
lesson_id int not null,
file_id int not null,
name varchar(300) not null,
delete_datetime datetime,
primary key (id),
foreign key (lesson_id) references crs_lessons(id),
foreign key (file_id) references crs_files(id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;