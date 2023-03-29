CREATE TABLE images (
id INT NOT NULL AUTO_INCREMENT,
image_file_name VARCHAR(500) NOT NULL,
status CHAR(1) NOT NULL,
PRIMARY KEY (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE images_diets (
id INT NOT NULL AUTO_INCREMENT,
image_id INT NOT NULL,
diet_id INT NOT NULL,
PRIMARY KEY (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE INDEX idx_image_id ON images_diets(image_id);
CREATE INDEX idx_diet_id ON images_diets(diet_id);





