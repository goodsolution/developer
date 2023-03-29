ALTER TABLE crs_orders ADD invoice_type varchar(1);
ALTER TABLE crs_orders ADD invoice_first_and_last_name varchar(300);
ALTER TABLE crs_orders ADD invoice_company_name varchar(300);
ALTER TABLE crs_orders ADD invoice_street varchar(300);
ALTER TABLE crs_orders ADD invoice_postal_code varchar(300);
ALTER TABLE crs_orders ADD invoice_city varchar(300);
ALTER TABLE crs_orders ADD invoice_nip varchar(300);
ALTER TABLE crs_orders ADD invoice_issued boolean;