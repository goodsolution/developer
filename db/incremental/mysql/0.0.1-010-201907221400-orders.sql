ALTER TABLE orders
ADD COLUMN order_date DATE;

update orders set order_date = order_datatime where order_date is null;

CREATE  INDEX idx_order_date ON orders(order_date);
--CREATE  INDEX idx_logged_buyer_email ON orders(logged_buyer_email);

CREATE INDEX idx_order_id ON delivery_orders(order_id);

CREATE INDEX idx_customer_city ON orders(customer_city);
CREATE INDEX idx_payment_id ON orders(payment_id);
CREATE INDEX idx_order_status_id ON orders(order_status_id);
CREATE INDEX idx_payment_status ON orders(payment_status);
CREATE INDEX idx_shipping_id ON orders(shipping_id);

CREATE INDEX idx_delivery_date ON delivery_orders(delivery_date);
CREATE INDEX idx_payment_update_date ON orders(payment_update_date);
