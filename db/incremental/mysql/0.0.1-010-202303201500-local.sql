create database developer_app;
use developer_app;

create table developer
(
    id                   int auto_increment,
    developer_name       varchar(200),
    address_street       varchar(200),
    address_number       varchar(200),
    address_postal_code  varchar(200),
    address_email        varchar(200),
    address_second_email varchar(200),
    nip_number           varchar(200),
    primary key (id)
);

create table estate
(
    id                     int auto_increment,
    estate_name            varchar(200),
    estate_district        varchar(200),
    estate_address         varchar(200),
    estate_description     varchar(5000),
    building_number        int,
    building_quantity      int,
    floors_number          int,
    locals_number          int,
    is_available           boolean,
    is_during_construction boolean,
    is_during_preparation  boolean,
    developer_id           int,
    primary key (id),
    foreign key (developer_id) REFERENCES developer (id)
);

create table sales_office
(
    id                       int auto_increment,
    developer_name           varchar(200),
    address_street           varchar(200),
    address_number           varchar(200),
    address_postal_code      varchar(200),
    address_email            varchar(200),
    address_second_email     varchar(200),
    opening_hours            varchar(200),
    sales_manager_name       varchar(200),
    sales_manager_tel_number varchar(200),
    primary key (id)
);

create table building
(
    id               int auto_increment,
    building_name    varchar(200),
    building_number  int,
    locals_available int,
    locals_reserved  int,
    locals_sold      int,
    estate_id        int,
    sales_office_id  int,
    primary key (id),
    foreign key (estate_id) references estate (id),
    foreign key (sales_office_id) references sales_office (id)
);

create table local
(
    id                    int auto_increment,
    developer             varchar(200),
    country               varchar(200),
    city                  varchar(200),
    type                  varchar(200),
    estate                varchar(200),
    building              varchar(200),
    address               varchar(200),
    local_number          int,
    floor_number          int(100),
    surface_sq_m          decimal(2),
    total_price           decimal(2),
    per_sq_m_price        decimal(2),
    rooms_quantity        int(100),
    is_special_offer      boolean,
    local_status          varchar(200),
    exposure              varchar(200),
    is_balcony            boolean,
    is_garden             boolean,
    is_terrace            boolean,
    is_loggia             boolean,
    ready_to_pick_up_date date,
    building_id int,
    primary key (id),
    foreign key (building_id) references building(id)
)
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB;

#                       -rzut lokalu
# 						-plan lokalu
# 						-wirtualny spacer 3D
# 						-widok elewacji
# 						-plan 3D
# 						-widok z okna
# 						-broszura do pobrania