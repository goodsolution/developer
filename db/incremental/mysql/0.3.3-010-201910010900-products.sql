update products
set gross_price_retail = REPLACE(gross_price_retail, ',', '.')
where gross_price_retail like '%,%';

update products
set gross_price_wholesale = REPLACE(gross_price_wholesale, ',', '.')
where gross_price_wholesale like '%,%';









