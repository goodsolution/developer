insert into developers (name, address_country, address_voivodeship, address_city, address_street,
                        address_building_number, address_flat_number, address_postal_code, telephone_number, fax_number,
                        email, tax_identification_number)
values ('Developer 3', 'Poland', 'Mazowieckie', 'Warszawa3', 'ul. 1', '1', '1', '00-000', '111111111', '222222222',
        'aa@wp.pl', '1111111111');
select *
from developers;

insert into investments (name, address_country, address_voivodeship, address_city, address_street, developer_id)
values ('Investment 3', 'Poland', 'Mazowieckie', 'Warszawa3', 'ul. 1', 3);

insert into sales_offices (address_country, address_voivodeship, address_city, address_street, address_building_number,
                           address_flat_number, address_postal_code, telephone_number, fax_number, email)
values ('Poland3', 'Mazowieckie', 'Warszawa3', 'ul. 1', '1', '1', '00-000', '111111111', '222222222', 'a@wp.pl');

insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('1', '08:00:00', '16:00:00', 1);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('2', '08:00:00', '16:00:00', 1);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('3', '08:00:00', '16:00:00', 1);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('4', '08:00:00', '16:00:00', 1);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('5', '08:00:00', '16:00:00', 1);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('6', '08:00:00', '16:00:00', 1);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('7', '08:00:00', '16:00:00', 1);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('1', '08:00:00', '16:00:00', 2);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('2', '08:00:00', '16:00:00', 2);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('3', '08:00:00', '16:00:00', 2);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('4', '08:00:00', '16:00:00', 2);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('5', '08:00:00', '16:00:00', 2);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('6', '08:00:00', '16:00:00', 2);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('7', '08:00:00', '16:00:00', 2);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('1', '08:00:00', '16:00:00', 3);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('2', '08:00:00', '16:00:00', 3);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('3', '08:00:00', '16:00:00', 3);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('4', '08:00:00', '16:00:00', 3);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('5', '08:00:00', '16:00:00', 3);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('6', '08:00:00', '16:00:00', 3);
insert into sales_office_opening_hours (day_of_week, time_open, time_closed, sales_office_id)
values ('7', '08:00:00', '16:00:00', 3);

insert into buildings (name, address_country, address_voivodeship, address_city, address_street,
                       address_building_number, address_postal_code, investment_id)
values ('Building 1', 'Poland', 'Mazowieckie', 'Warszawa3', 'ul. 1', '1', '00-000', 1);
insert into buildings (name, address_country, address_voivodeship, address_city, address_street,
                       address_building_number, address_postal_code, investment_id)
values ('Building 2', 'Poland', 'Mazowieckie', 'Warszawa3', 'ul. 1', '1', '00-000', 2);
insert into buildings (name, address_country, address_voivodeship, address_city, address_street,
                       address_building_number, address_postal_code, investment_id)
values ('Building 3', 'Poland', 'Mazowieckie', 'Warszawa3', 'ul. 1', '1', '00-000', 3);

insert into premises (type, number, floor, surface_sq_m, price_of_sq_m, price_total, number_of_rooms, technical_status,
                      sales_status, exposure, is_balcony, is_garden, is_terrace, is_loggia, building_id)
values ('a', '1', '1', '100', '1000', '100000', '1', 'finished', 'available', 'west', '1', '1', '1', '1', 1);
insert into premises (type, number, floor, surface_sq_m, price_of_sq_m, price_total, number_of_rooms, technical_status,
                      sales_status, exposure, is_balcony, is_garden, is_terrace, is_loggia, building_id)
values ('a', '1', '1', '100', '1000', '100000', '1', 'finished', 'available', 'west', '1', '1', '1', '1', 1);
insert into premises (type, number, floor, surface_sq_m, price_of_sq_m, price_total, number_of_rooms, technical_status,
                      sales_status, exposure, is_balcony, is_garden, is_terrace, is_loggia, building_id)
values ('a', '1', '1', '100', '1000', '100000', '1', 'finished', 'available', 'west', '1', '1', '1', '1', 1);

insert into premises (type, number, floor, surface_sq_m, price_of_sq_m, price_total, number_of_rooms, technical_status,
                      sales_status, exposure, is_balcony, is_garden, is_terrace, is_loggia, building_id)
values ('a', '1', '1', '100', '1000', '100000', '1', 'finished', 'available', 'west', '1', '1', '1', '1', 2);
insert into premises (type, number, floor, surface_sq_m, price_of_sq_m, price_total, number_of_rooms, technical_status,
                      sales_status, exposure, is_balcony, is_garden, is_terrace, is_loggia, building_id)
values ('a', '1', '1', '100', '1000', '100000', '1', 'finished', 'available', 'west', '1', '1', '1', '1', 2);
insert into premises (type, number, floor, surface_sq_m, price_of_sq_m, price_total, number_of_rooms, technical_status,
                      sales_status, exposure, is_balcony, is_garden, is_terrace, is_loggia, building_id)
values ('a', '1', '1', '100', '1000', '100000', '1', 'finished', 'available', 'west', '1', '1', '1', '1', 2);

insert into premises (type, number, floor, surface_sq_m, price_of_sq_m, price_total, number_of_rooms, technical_status,
                      sales_status, exposure, is_balcony, is_garden, is_terrace, is_loggia, building_id)
values ('a', '1', '1', '100', '1000', '100000', '1', 'finished', 'available', 'west', '1', '1', '1', '1', 3);
insert into premises (type, number, floor, surface_sq_m, price_of_sq_m, price_total, number_of_rooms, technical_status,
                      sales_status, exposure, is_balcony, is_garden, is_terrace, is_loggia, building_id)
values ('a', '1', '1', '100', '1000', '100000', '1', 'finished', 'available', 'west', '1', '1', '1', '1', 3);
insert into premises (type, number, floor, surface_sq_m, price_of_sq_m, price_total, number_of_rooms, technical_status,
                      sales_status, exposure, is_balcony, is_garden, is_terrace, is_loggia, building_id)
values ('a', '1', '1', '100', '1000', '100000', '1', 'finished', 'available', 'west', '1', '1', '1', '1', 3);

insert into employees (first_name, last_name, position, address_city, address_street, address_building_number,
                       address_postal_code, address_country, address_voivodeship, personal_id_number, developer_id)
values ('Jan', 'Kowalski', 'developer', 'Warszawa', 'ul. 1', '1', '00-000', 'Poland', 'Mazowieckie', '12345678901', 1);

insert into employees (first_name, last_name, position, address_city, address_street, address_building_number,
                       address_postal_code, address_country, address_voivodeship, personal_id_number, developer_id)
values ('Jan', 'Kowalski', 'developer', 'Warszawa', 'ul. 1', '1', '00-000', 'Poland', 'Mazowieckie', '12345678901', 1);

insert into employees (first_name, last_name, position, address_city, address_street, address_building_number,
                       address_postal_code, address_country, address_voivodeship, personal_id_number, developer_id)
values ('Jan2', 'Kowalski', 'developer', 'Warszawa', 'ul. 1', '1', '00-000', 'Poland', 'Mazowieckie', '12345678901', 2);

insert into employees (first_name, last_name, position, address_city, address_street, address_building_number,
                       address_postal_code, address_country, address_voivodeship, personal_id_number, developer_id)
values ('Jan2', 'Kowalski', 'developer', 'Warszawa', 'ul. 1', '1', '00-000', 'Poland', 'Mazowieckie', '12345678901', 2);

insert into employees (first_name, last_name, position, address_city, address_street, address_building_number,
                       address_postal_code, address_country, address_voivodeship, personal_id_number, developer_id)
values ('Jan3', 'Kowalski', 'developer', 'Warszawa', 'ul. 1', '1', '00-000', 'Poland', 'Mazowieckie', '12345678901', 3);

insert into employees (first_name, last_name, position, address_city, address_street, address_building_number,
                       address_postal_code, address_country, address_voivodeship, personal_id_number, developer_id)
values ('Jan3', 'Kowalski', 'developer', 'Warszawa', 'ul. 1', '1', '00-000', 'Poland', 'Mazowieckie', '12345678901', 3);

insert into sales_offices_with_employees(employee_id, sales_office_id)
values (1, 1);
insert into sales_offices_with_employees(employee_id, sales_office_id)
values (2, 1);
insert into sales_offices_with_employees(employee_id, sales_office_id)
values (3, 2);
insert into sales_offices_with_employees(employee_id, sales_office_id)
values (4, 2);
insert into sales_offices_with_employees(employee_id, sales_office_id)
values (5, 3);
insert into sales_offices_with_employees(employee_id, sales_office_id)
values (6, 3);

insert into investments_with_sales_offices(investment_id, sales_office_id)
values (1, 1);
insert into investments_with_sales_offices(investment_id, sales_office_id)
values (2, 2);
insert into investments_with_sales_offices(investment_id, sales_office_id)
values (3, 3);

insert into customers (first_name, last_name, private_address_city, private_address_street,
                       private_address_building_number, private_address_postal_code)
values ('Jan', 'Kowalski', 'Warszawa', 'ul. 1', '1', '00-000');
insert into customers (first_name, last_name, private_address_city, private_address_street,
                       private_address_building_number, private_address_postal_code)
values ('Jan2', 'Kowalski', 'Warszawa', 'ul. 1', '1', '00-000');
insert into customers (first_name, last_name, private_address_city, private_address_street,
                       private_address_building_number, private_address_postal_code)
values ('Jan3', 'Kowalski', 'Warszawa', 'ul. 1', '1', '00-000');


select *
from premises
where building_id = 1
  and is_balcony = 1;

select id, type, price_total
from premises
where surface_sq_m > 30
  and number_of_rooms = 2
  and building_id = 1
  and sales_status = 'a';


select *
from premises p
         join buildings b on b.id = p.building_id
where b.address_city = 'Warszawa3'
  and p.sales_status = 'a'
  and p.price_total BETWEEN 90000 AND 200000
  and p.surface_sq_m BETWEEN 70 AND 200
  and p.exposure = 'west'
  and p.number_of_rooms = 2
  and p.type = 'a'
  and p.is_balcony = 1;


select count(p.id)
from developer_project.buildings
         join premises p on buildings.id = p.building_id
where buildings.address_city = 'Warszawa3'
  and p.sales_status = 'a';

select *
from employees e
         join sales_offices s on e.id = s.id
         join investments_with_sales_offices i on s.id = i.sales_office_id
where i.investment_id = 1;

select premises.id, premises.price_of_sq_m
from premises
         join buildings b on b.id = premises.building_id
where premises.number_of_rooms = 2
  AND premises.surface_sq_m BETWEEN 70 AND 200
  AND premises.floor = 1
  AND b.investment_id = 1
  AND premises.sales_status = 'a'
  AND premises.type = 'a'
  AND premises.is_balcony = 1;

select *
from premises as p
         join buildings as b on b.id = p.building_id
         join investments as i on i.id = b.investment_id
where i.id = 1
  and p.sales_status = 'a'
  and p.type = 'a'
  and p.number_of_rooms = 2
  and p.surface_sq_m BETWEEN 70 AND 200;