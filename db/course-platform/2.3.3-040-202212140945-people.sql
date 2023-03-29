create table people
(
    id              int auto_increment primary key,
    first_name       varchar(100) not null,
    last_name        varchar(100) not null,
    email            varchar(500),
    phone            varchar(500),
    create_datetime datetime      not null,
    delete_datetime datetime,
    modify_datetime datetime

)
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

create table resources
(
    id              int auto_increment primary key,
    name            varchar(500) not null,
    create_datetime datetime      not null,
    delete_datetime datetime,
    modify_datetime datetime

)
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

create table people_resources
(
    id              int auto_increment primary key,
    people_id       int not null,
    resource_id     int not null,
    create_datetime datetime      not null,

    foreign key (people_id) references people(id),
    foreign key (resource_id) references resources(id)

)
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;

