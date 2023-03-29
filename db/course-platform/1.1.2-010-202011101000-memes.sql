create table crs_memes(
id int auto_increment,
title varchar(200) not null,
file_name varchar(300) not null,
description varchar(5000) not null,
keywords varchar(500) not null,
creation_datetime datetime not null,
language varchar(20) not null,
primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;