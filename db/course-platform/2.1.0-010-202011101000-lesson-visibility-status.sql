ALTER TABLE crs_lessons ADD visibility_status varchar(1);

SET SQL_SAFE_UPDATES = 0;
UPDATE crs_lessons SET visibility_status = 'v' WHERE visibility_status IS NULL;
SET SQL_SAFE_UPDATES = 1;