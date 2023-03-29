create table order_discounts_applied (
    id INT NOT NULL AUTO_INCREMENT,
    order_id INT NOT NULL,
    discount_type VARCHAR(512) NOT NULL,
    discount_value FLOAT  NOT NULL,
    discount_value_total FLOAT NOT NULL,
    price_before FLOAT NOT NULL,
    price_after FLOAT NOT NULL,
    create_date DATETIME NOT NULL,
    PRIMARY KEY (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE INDEX idx_order_id ON order_discounts_applied(order_id);