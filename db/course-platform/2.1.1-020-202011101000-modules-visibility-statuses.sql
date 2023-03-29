ALTER TABLE crs_modules ADD visibility_status varchar(1);

SET SQL_SAFE_UPDATES = 0;
UPDATE crs_modules SET visibility_status = 'v' WHERE visibility_status IS NULL;
SET SQL_SAFE_UPDATES = 1;