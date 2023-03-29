ALTER TABLE crs_memes ADD code varchar(300);
CREATE UNIQUE INDEX code_unique ON crs_memes(code);