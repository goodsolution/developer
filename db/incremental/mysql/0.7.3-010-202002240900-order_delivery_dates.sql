create table order_delivery_dates (
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

CREATE INDEX idx_order_id ON order_delivery_dates(order_id);

alter table order_delivery_dates add column status CHAR(1) NOT NULL DEFAULT 'N';

alter table orders add column new_calendar INT;

CREATE INDEX idx_order_product_id ON order_delivery_dates(order_product_id);