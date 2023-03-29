create table tub_movies
(
    id              int auto_increment primary key,
    title_pl        varchar(1000) not null,
    title_en        varchar(1000) not null,
    keywords        varchar(1000),
    link            varchar(1000) not null,
    create_datetime datetime      not null,
    delete_datetime datetime
)
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;
