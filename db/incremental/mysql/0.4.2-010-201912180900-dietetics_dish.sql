ALTER TABLE dietetics_dishs ADD COLUMN fish_bone int(11) not null default 0;
CREATE INDEX idx_product_id ON products_languages(product_id);
CREATE INDEX idx_category_id ON products(category_id);







