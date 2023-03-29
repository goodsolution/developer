alter table order_delivery_dates_original add column quantity INT NOT NULL DEFAULT 0;
alter table order_delivery_dates add column quantity INT NOT NULL DEFAULT 0;

alter table order_delivery_dates_original add column source VARCHAR(1) NOT NULL DEFAULT 'F';
alter table order_delivery_dates add column source VARCHAR(1) NOT NULL DEFAULT 'F';

alter table order_delivery_dates add column delivery_info VARCHAR(1) NOT NULL DEFAULT 'N';

alter table delivery_orders add column original_delivery_date DATE;

alter table instagram_images modify column alt VARCHAR(1024);
alter table instagram_images modify column title VARCHAR(1024);