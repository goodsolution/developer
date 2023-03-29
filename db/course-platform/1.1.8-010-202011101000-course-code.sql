ALTER TABLE crs_courses ADD code varchar(300);
CREATE UNIQUE INDEX code_unique ON crs_courses(code);