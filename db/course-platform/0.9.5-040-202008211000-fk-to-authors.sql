ALTER TABLE crs_courses ADD author_id INT NOT NULL;
ALTER TABLE crs_courses ADD CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES crs_authors(id);