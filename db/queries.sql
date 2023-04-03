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

select e.first_name, e.last_name, e.position, e.business_telephone_number
from employees e
         join sales_offices s on e.id = s.id
where (s.id = (select s.id
               from sales_offices s
                        join investments_with_sales_offices
                             on s.id = investments_with_sales_offices.sales_office_id
               where investments_with_sales_offices.investment_id = 1)
          );

select * from premises join buildings b on b.id = premises.building_id where premises.number_of_rooms = 2 AND premises.surface_sq_m BETWEEN 70 AND 200 AND premises.floor = 1
and b.investment_id = 1 and premises.sales_status = 'available' and premises.type = 'apartment' and premises.is_balcony = 1;