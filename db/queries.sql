select *
from premises
where building_id = 1
  and is_balcony = 1;


select id, type, price_total
from premises
where surface_sq_m > 30
  and number_of_rooms = 2
  and building_id = 1
  and sales_status = 'available';


select *
from premises p
         join buildings b on b.id = p.building_id
where b.address_city = 'Warszawa3'
  and p.sales_status = 'available'
  and p.price_total BETWEEN 90000 AND 200000
  and p.surface_sq_m BETWEEN 70 AND 200
  and p.exposure = 'west'
  and p.number_of_rooms = 2
  and p.type = 'apartment'
  and p.is_balcony = 1;


select count(p.id) as available_apartments_from_Warsaw
from buildings
         join premises p on buildings.id = p.building_id
where buildings.address_city = 'Warszawa3'
  and p.sales_status = 'available';

select *
from employees e
         join sales_offices s on e.id = s.id
         join investments_with_sales_offices i on s.id = i.sales_office_id
where i.investment_id = 1;

select premises.id as apartment_id, premises.price_of_sq_m
from premises
         join buildings b on b.id = premises.building_id
where premises.number_of_rooms = 2
  AND premises.surface_sq_m BETWEEN 70 AND 200
  AND premises.floor = 1
  AND b.investment_id = 1
  AND premises.sales_status = 'available'
  AND premises.type = 'apartment'
  AND premises.is_balcony = 1;

select *
from premises as p
         join buildings as b on b.id = p.building_id
where (
        b.id = (select b.id
                from buildings
                         join investments i on i.id = buildings.investment_id
                where i.id = 1)
    )
  and p.sales_status = 'available'
  and p.type = 'apartment'
  and p.number_of_rooms = 2
  and p.surface_sq_m BETWEEN 70 AND 200;