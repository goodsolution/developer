CREATE  INDEX idx_order_id ON order_payments(order_id);
CREATE  INDEX idx_tax_id ON tax_table(tax_id);
CREATE  INDEX idx_tax_id ON orders_products(tax_id);

CREATE  INDEX idx_delivery_id ON delivery_pictures(delivery_id);
CREATE  INDEX idx_send_by ON order_mail_sends(send_by);
CREATE  INDEX idx_order_id ON delivery_changes(order_id);
CREATE  INDEX idx_edited_by ON delivery_changes(edited_by);
CREATE  INDEX idx_edited_by ON orders_versions(edited_by);
CREATE  INDEX idx_order_id ON orders_versions(order_id);
CREATE  INDEX idx_version_id ON orders_products_versions(version_id);

CREATE  INDEX idx_code_id ON discount_codes_usages(code_id);
CREATE  INDEX idx_product_id ON orders_products(product_id);



