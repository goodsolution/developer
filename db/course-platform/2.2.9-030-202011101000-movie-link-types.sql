ALTER TABLE crs_lessons ADD movie_link_type VARCHAR(1);
ALTER TABLE crs_lessons ADD task_solution_movie_link_type VARCHAR(1);

UPDATE crs_lessons SET movie_link_type = 'n' WHERE movie_link_type IS NULL AND (movie_link = '' OR movie_link IS NULL);
UPDATE crs_lessons SET movie_link_type = 'y' WHERE movie_link_type IS NULL;

UPDATE crs_lessons SET task_solution_movie_link_type = 'n' WHERE task_solution_movie_link_type IS NULL AND (task_solution_movie_link = '' OR task_solution_movie_link IS NULL);
UPDATE crs_lessons SET task_solution_movie_link_type = 'y' WHERE task_solution_movie_link_type IS NULL;

ALTER TABLE crs_lessons MODIFY movie_link_type varchar(1) NOT NULL;
ALTER TABLE crs_lessons MODIFY task_solution_movie_link_type varchar(1) NOT NULL;