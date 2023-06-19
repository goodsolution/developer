# creates

CREATE TABLE IF NOT EXISTS voivodeships
(
    id   int auto_increment,
    name varchar(200),
    PRIMARY KEY (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS cities
(
    id             int auto_increment,
    name           varchar(200) NOT NULL,
    voivodeship_id int          NOT NULL,
    PRIMARY KEY (id),
    foreign key (voivodeship_id) references voivodeships (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

create table developers
(
    id                        int auto_increment,
    name                      varchar(200) NOT NULL,
    address_country           varchar(200) NOT NULL,
#     address_voivodeship       varchar(200),
#     address_city              varchar(200) NOT NULL,
    address_street            varchar(200) NOT NULL,
    address_building_number   varchar(200) NOT NULL,
    address_flat_number       varchar(200),
    address_postal_code       varchar(200),
    telephone_number          varchar(200),
    fax_number                varchar(200),
    email                     varchar(200),
    tax_identification_number varchar(200) NOT NULL,
    city_id                   int          NOT NULL,
    voivodeship_id            int          NOT NULL,
    primary key (id),
    foreign key (city_id) references cities (id),
    foreign key (voivodeship_id) references voivodeships (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

create table investments
(
    id              int auto_increment,
    name            varchar(200),
    description     varchar(5000),
    address_country varchar(200),
#     address_voivodeship varchar(200),
#     address_city        varchar(200) NOT NULL,
    address_street  varchar(200),
    developer_id    int NOT NULL,
    city_id         int NOT NULL,
    voivodeship_id  int NOT NULL,
    primary key (id),
    foreign key (city_id) references cities (id),
    foreign key (voivodeship_id) references voivodeships (id),
    foreign key (developer_id) REFERENCES developers (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

create table sales_offices
(
    id                      int auto_increment,
    address_country         varchar(200),
#     address_voivodeship     varchar(200),
#     address_city            varchar(200) NOT NULL,
    address_street          varchar(200) NOT NULL,
    address_building_number varchar(200) NOT NULL,
    address_flat_number     varchar(200),
    address_postal_code     varchar(200),
    telephone_number        varchar(200),
    fax_number              varchar(200),
    email                   varchar(200),
    city_id                 int          NOT NULL,
    voivodeship_id          int          NOT NULL,
    primary key (id),
    foreign key (city_id) references cities (id),
    foreign key (voivodeship_id) references voivodeships (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

create table sales_office_opening_hours
(
    id              int auto_increment,
    day_of_week     varchar(200) NOT NULL,
    time_open       time         NOT NULL,
    time_closed     time         NOT NULL,
    sales_office_id int          NOT NULL,
    foreign key (sales_office_id) REFERENCES sales_offices (id),
    primary key (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

create table investments_with_sales_offices
(
    id              int auto_increment,
    sales_office_id int NOT NULL,
    investment_id   int NOT NULL,
    foreign key (sales_office_id) REFERENCES sales_offices (id),
    foreign key (investment_id) REFERENCES investments (id),
    primary key (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

create table employees
(
    id                        int auto_increment,
    first_name                varchar(200) NOT NULL,
    last_name                 varchar(200) NOT NULL,
    position                  varchar(200) NOT NULL,
    business_email            varchar(200),
    business_telephone_number varchar(200),
    address_country           varchar(200),
    address_voivodeship       varchar(200),
    address_city              varchar(200) NOT NULL,
    address_street            varchar(200) NOT NULL,
    address_building_number   varchar(200) NOT NULL,
    address_flat_number       varchar(200),
    address_postal_code       varchar(200) NOT NULL,
    private_telephone_number  varchar(200),
    private_email             varchar(200),
    personal_id_number        varchar(200) NOT NULL,
    developer_id              int          NOT NULL,
    foreign key (developer_id) REFERENCES developers (id),
    primary key (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

create table sales_offices_with_employees
(
    id              int auto_increment,
    employee_id     int NOT NULL,
    sales_office_id int NOT NULL,
    primary key (id),
    foreign key (employee_id) references employees (id),
    foreign key (sales_office_id) references sales_offices (id)
)
    ENGINE INNODB
    COLLATE utf8_general_ci;

create table customers
(
    id                                int auto_increment,
    first_name                        varchar(200) NOT NULL,
    last_name                         varchar(200) NOT NULL,
    private_address_country           varchar(200),
    private_address_voivodeship       varchar(200),
    private_address_city              varchar(200) NOT NULL,
    private_address_street            varchar(200) NOT NULL,
    private_address_building_number   varchar(200) NOT NULL,
    private_address_flat_number       varchar(200),
    private_address_postal_code       varchar(200) NOT NULL,
    private_telephone_number          varchar(200),
    private_fax_number                varchar(200),
    private_email                     varchar(200),
    personal_id_number                varchar(200),
    company_name                      varchar(200),
    company_address_country           varchar(200),
    company_address_city              varchar(200),
    company_address_street            varchar(200),
    company_address_building_number   varchar(200),
    company_address_flat_number       varchar(200),
    company_address_postal_code       varchar(200),
    company_telephone_number          varchar(200),
    company_fax_number                varchar(200),
    company_email                     varchar(200),
    company_tax_identification_number varchar(200),
    developer_id                      int          NOT NULL,
    primary key (id),
    foreign key (developer_id) references developers (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

create table buildings
(
    id                      int auto_increment,
    name                    varchar(200),
    address_country         varchar(200),
#     address_voivodeship     varchar(200),
#     address_city            varchar(200),
    address_street          varchar(200) NOT NULL,
    address_building_number varchar(200),
    address_postal_code     varchar(200),
    investment_id           int          NOT NULL,
    city_id                 int          NOT NULL,
    voivodeship_id          int          NOT NULL,
    primary key (id),
    foreign key (investment_id) references investments (id),
    foreign key (city_id) references cities (id),
    foreign key (voivodeship_id) references voivodeships (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

create table premises
(
    id               int auto_increment,
    type             varchar(200)   NOT NULL,
    number           int            NOT NULL,
    floor            int(100)       NOT NULL,
    surface_sq_m     decimal(16, 2) NOT NULL,
    price_of_sq_m    decimal(16, 2),
    price_total      decimal(16, 2),
    number_of_rooms  int(100)       NOT NULL,
    technical_status varchar(200),
    sales_status     varchar(200),
    exposure         varchar(200),
    is_balcony       tinyint,
    is_garden        tinyint,
    is_terrace       tinyint,
    is_loggia        tinyint,
    building_id      int            NOT NULL,
    primary key (id),
    foreign key (building_id) references buildings (id)
)
    ENGINE INNODB
    COLLATE utf8mb4_general_ci;

# inserts
INSERT INTO voivodeships (name)
VALUES ('DOLNOŚLĄSKIE'),
       ('KUJAWSKO-POMORSKIE'),
       ('LUBELSKIE'),
       ('LUBUSKIE'),
       ('ŁÓDZKIE'),
       ('MAŁOPOLSKIE'),
       ('MAZOWIECKIE'),
       ('OPOLSKIE'),
       ('PODKARPACKIE'),
       ('PODLASKIE'),
       ('POMORSKIE'),
       ('ŚLĄSKIE'),
       ('ŚWIĘTOKRZYSKIE'),
       ('WARMIŃSKO-MAZURSKIE'),
       ('WIELKOPOLSKIE'),
       ('ZACHODNIOPOMORSKIE');

INSERT INTO cities (voivodeship_id, name)
VALUES (1, 'WROCŁAW'),
       (2, 'BYDGOSZCZ'),
       (3, 'LUBLIN'),
       (4, 'ZIELONA GÓRA'),
       (5, 'ŁÓDŹ'),
       (6, 'KRAKÓW'),
       (7, 'WARSZAWA'),
       (8, 'OPOLE'),
       (9, 'RZESZÓW'),
       (10, 'BIAŁYSTOK'),
       (11, 'GDAŃSK'),
       (12, 'KATOWICE'),
       (13, 'KIELCE'),
       (14, 'OLSZTYN'),
       (15, 'POZNAŃ'),
       (16, 'SZCZECIN');

INSERT INTO developers (name, address_country, address_street,
                        address_building_number, address_flat_number, address_postal_code,
                        telephone_number, fax_number,
                        email, tax_identification_number, city_id, voivodeship_id)
VALUES ('Antal', 'Poland', 'ul. Testowa', '1', '1', '00-001', '123456789', '123456789', 'dev_01@wp.pl', '123456789', 7,
        7);

INSERT INTO developers (name, address_country, address_street,
                        address_building_number, address_flat_number, address_postal_code,
                        telephone_number, fax_number,
                        email, tax_identification_number, city_id, voivodeship_id)
VALUES ('Murapol', 'Poland', 'ul. Testowa', '1', '1', '00-001', '123456789', '123456789', 'dev_01@wp.pl', '123456789',
        6, 6);

INSERT INTO investments (name, description, address_country, address_street, developer_id, city_id, voivodeship_id)
VALUES ('Investment_01', 'Description_01', 'Poland', 'ul. Testowa', 1, 7, 7);
INSERT INTO investments (name, description, address_country, address_street, developer_id, city_id, voivodeship_id)
VALUES ('Investment_02', 'Description_01', 'Poland', 'ul. Testowa', 1, 7, 7);
INSERT INTO investments (name, description, address_country, address_street, developer_id, city_id, voivodeship_id)
VALUES ('Investment_03', 'Description_01', 'Poland', 'ul. Testowa', 2, 7, 7);
INSERT INTO investments (name, description, address_country, address_street, developer_id, city_id, voivodeship_id)
VALUES ('Investment_04', 'Description_01', 'Poland', 'ul. Testowa', 1, 12, 12);
INSERT INTO investments (name, description, address_country, address_street, developer_id, city_id, voivodeship_id)
VALUES ('Investment_05', 'Description_01', 'Poland', 'ul. Testowa', 1, 4, 4);

INSERT INTO buildings (name, address_country, address_street, address_building_number, address_postal_code,
                       investment_id, city_id, voivodeship_id)
VALUES ('Building_01', 'Poland', 'ul. Testowa', '1', '00-001', 1, 7, 7);
INSERT INTO buildings (name, address_country, address_street, address_building_number, address_postal_code,
                       investment_id, city_id, voivodeship_id)
VALUES ('Building_02', 'Poland', 'ul. Testowa', '1', '00-001', 1, 7, 7);
INSERT INTO buildings (name, address_country, address_street, address_building_number, address_postal_code,
                       investment_id, city_id, voivodeship_id)
VALUES ('Building_03', 'Poland', 'ul. Testowa', '1', '00-001', 1, 4, 4);
INSERT INTO buildings (name, address_country, address_street, address_building_number, address_postal_code,
                       investment_id, city_id, voivodeship_id)
VALUES ('Building_04', 'Poland', 'ul. Testowa', '1', '00-001', 2, 7, 7);
INSERT INTO buildings (name, address_country, address_street, address_building_number, address_postal_code,
                       investment_id, city_id, voivodeship_id)
VALUES ('Building_05', 'Poland', 'ul. Testowa', '1', '00-001', 2, 7, 7);
INSERT INTO buildings (name, address_country, address_street, address_building_number, address_postal_code,
                       investment_id, city_id, voivodeship_id)
VALUES ('Building_06', 'Poland', 'ul. Testowa', '1', '00-001', 2, 7, 7);
INSERT INTO buildings (name, address_country, address_street, address_building_number, address_postal_code,
                       investment_id, city_id, voivodeship_id)
VALUES ('Building_07', 'Poland', 'ul. Testowa', '1', '00-001', 2, 7, 7);
INSERT INTO buildings (name, address_country, address_street, address_building_number, address_postal_code,
                       investment_id, city_id, voivodeship_id)
VALUES ('Building_08', 'Poland', 'ul. Testowa', '1', '00-001', 2, 7, 7);


INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 1);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 1);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 1);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('b', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 1);

INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 2);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 2);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 2);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('b', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 2);

INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 3);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 3);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 3);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('b', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 3);

INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 4);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 4);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 4);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('b', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 4);

INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 5);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 5);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 5);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('b', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 5);

INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 6);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 6);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 6);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('b', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 6);

INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 7);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 7);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 7);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('b', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 7);

INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 8);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 8);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('a', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 8);
INSERT INTO premises (TYPE, NUMBER, FLOOR, SURFACE_SQ_M, PRICE_OF_SQ_M, PRICE_TOTAL, NUMBER_OF_ROOMS, TECHNICAL_STATUS,
                      SALES_STATUS, EXPOSURE, IS_BALCONY, IS_GARDEN, IS_TERRACE, IS_LOGGIA, BUILDING_ID)
VALUES ('b', '1', '1', '50', '1000', '50000', '2', 'a', 'r', 'w', '1', '1', '0', '0', 8);


# selected queries

select *
from premises
where building_id = 1
  and is_balcony = 1;

select *
from premises
where surface_sq_m > 30
  and number_of_rooms = 2
  and building_id = 1
  and sales_status = 'r';

select *
from premises p
         join buildings b on b.id = p.building_id
where b.address_city = 'Warszawa'
  and p.sales_status = 'r'
  and p.price_total BETWEEN 0 AND 1000000
  and p.surface_sq_m BETWEEN 0 AND 1000000
  and p.exposure = 'w'
  and p.number_of_rooms = 2
  and p.type = 'a'
  and p.is_balcony = 1;

select count(p.id)
from developer_project.buildings b
         join premises p on b.id = p.building_id
where b.address_city = 'Warszawa'
  and p.sales_status = 'r';

select *
from employees e
         JOIN sales_offices_with_employees sowe on e.id = sowe.employee_id
         JOIN investments_with_sales_offices iwso on sowe.sales_office_id = iwso.sales_office_id
where iwso.investment_id = 3;

select *
from premises
         join buildings b on b.id = premises.building_id
where premises.number_of_rooms = 2
  AND premises.surface_sq_m BETWEEN 40 AND 200
  AND premises.floor = 1
  AND b.investment_id = 1
  AND premises.sales_status = 'r'
  AND premises.type = 'a'
  AND premises.is_balcony = 1;

select *
from premises as p
         join buildings as b on b.id = p.building_id
         join investments as i on i.id = b.investment_id
where i.id = 1
  and p.sales_status = 'r'
  and p.type = 'a'
  and p.number_of_rooms = 2
  and p.surface_sq_m BETWEEN 40 AND 200;

select investments.address_city, count(investments.address_city)
from investments
where investments.developer_id = 1
group by investments.address_city;

# home page display
SELECT COUNT(city_id) AS city_count
FROM investments
WHERE developer_id = 1
GROUP BY city_id;

SELECT c.name, COUNT(premises.sales_status) AS sales_status_count
FROM investments
         JOIN cities c on investments.city_id = c.id
         JOIN buildings ON investments.id = buildings.investment_id
         JOIN premises ON buildings.id = premises.building_id
WHERE premises.sales_status = 'a'
  AND investments.developer_id = 1
GROUP BY c.id;

# query to get available premises and number of invstms in city
SELECT c.name, city_count, COUNT(premises.id) AS sales_status_count
FROM (SELECT city_id, COUNT(city_id) AS city_count
      FROM investments
      WHERE developer_id = 1
      GROUP BY city_id) AS subquery
         JOIN investments ON subquery.city_id = investments.city_id
         JOIN cities c on c.id = investments.city_id
         JOIN buildings ON investments.id = buildings.investment_id
         JOIN premises ON buildings.id = premises.building_id
    AND premises.sales_status = 'a'
GROUP BY investments.city_id;

select distinct cities.name
from cities
         join investments i on cities.id = i.city_id
         join developers d on i.developer_id = d.id
where d.id = 1;

select c.name, count(investments.id)
from investments
         join cities c on c.id = investments.city_id
where developer_id = 1
group by investments.city_id;

select c.name, count(p.id)
from investments
    join cities c on investments.city_id = c.id
         join buildings b on investments.id = b.investment_id
         join premises p on b.id = p.building_id
where developer_id = 1
  and p.sales_status = 'a'
group by investments.city_id;
