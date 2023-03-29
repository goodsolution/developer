ALTER TABLE crs_lessons ADD type varchar(1) NOT NULL DEFAULT 's';
ALTER TABLE crs_lessons ADD task_solution_description varchar(5000);
ALTER TABLE crs_lessons ADD task_solution_movie_link varchar(500);