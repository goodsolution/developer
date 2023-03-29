ALTER TABLE crs_customers ADD author_id int;
ALTER TABLE crs_customers ADD CONSTRAINT fkey_author_id FOREIGN KEY (author_id) REFERENCES crs_authors(id);