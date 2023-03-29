ALTER TABLE crs_customers ADD is_enabled boolean;

SET SQL_SAFE_UPDATES = 0;
update crs_customers set is_enabled=true where is_enabled IS NULL;
SET SQL_SAFE_UPDATES = 1;