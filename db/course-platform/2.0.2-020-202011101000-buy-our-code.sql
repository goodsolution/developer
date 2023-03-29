ALTER TABLE crs_courses ADD type varchar(1);

SET SQL_SAFE_UPDATES = 0;
UPDATE crs_courses SET type = 'c' where type IS NULL;
SET SQL_SAFE_UPDATES = 1;