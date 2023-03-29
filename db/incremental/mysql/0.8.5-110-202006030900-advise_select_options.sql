create table advice_answer_options (
    id int auto_increment,
    advice_id int NOT NULL,
    option_name varchar(1000) NOT NULL,
    value varchar(100) NOT NULL,
    option_order int DEFAULT 0 NOT NULL,
    primary key (id)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
;
