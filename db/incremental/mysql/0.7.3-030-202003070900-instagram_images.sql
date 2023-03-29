create table instagram_images (
    id INT NOT NULL AUTO_INCREMENT,
    href VARCHAR(512) NOT NULL,
    src VARCHAR(512) NOT NULL,
    alt VARCHAR(512),
    title VARCHAR(512),
    refresh_date DATETIME NOT NULL,
    PRIMARY KEY (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

