create table order_delivery_dates_original (
    id INT NOT NULL AUTO_INCREMENT,
    delivery_date DATE NOT NULL,
    creation_date DATE NOT NULL,
    order_product_id INT NOT NULL,
    order_id INT NOT NULL,
    PRIMARY KEY (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE INDEX idx_order_id ON order_delivery_dates_original(order_id);

CREATE INDEX idx_order_product_id ON order_delivery_dates_original(order_product_id);
