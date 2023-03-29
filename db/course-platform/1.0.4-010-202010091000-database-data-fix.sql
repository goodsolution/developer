SET SQL_SAFE_UPDATES = 0;
update crs_orders set invoice_country='pl' where invoice_country IS NULL;
update crs_customers set invoice_country='pl' where invoice_country IS NULL;
SET SQL_SAFE_UPDATES = 1;