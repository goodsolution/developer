ALTER TABLE orders
ADD COLUMN create_datetime DATETIME;

ALTER TABLE orders
ADD COLUMN column_version INT(11);

update orders set create_datetime = order_datatime, column_version = 0;

ALTER TABLE orders
MODIFY COLUMN column_version INT(11) NOT NULL default 1 COMMENT '0-init, 1-create_datetime';

ALTER TABLE orders
MODIFY COLUMN create_datetime DATETIME NOT NULL default NOW();

CREATE INDEX idx_create_datetime ON orders(create_datetime);
-- CREATE INDEX idx_user_order_id ON orders(user_order_id); text column

ALTER TABLE orders
ADD COLUMN customer_city_id INT(11);

ALTER TABLE orders
ADD COLUMN weekend_city_id INT(11);

ALTER TABLE orders
ADD COLUMN invoice_city_id INT(11);

ALTER TABLE orders
ADD COLUMN shipping_city_id INT(11);






