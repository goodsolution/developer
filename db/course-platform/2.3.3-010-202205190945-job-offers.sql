create table crs_job_offers
(
    id                  int auto_increment,
    create_datetime     datetime      not null,
    delete_datetime     datetime,
    creator_customer_id int           not null,
    title               varchar(200)  not null,
    content             varchar(2000) not null,
    primary key (id),
    foreign key (creator_customer_id) references crs_customers (id)
)
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
;