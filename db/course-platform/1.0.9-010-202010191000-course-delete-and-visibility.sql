ALTER TABLE crs_courses ADD visibility_status varchar(1) DEFAULT 'v';
ALTER TABLE crs_courses ADD delete_datetime datetime;