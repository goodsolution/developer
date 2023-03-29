SET SQL_SAFE_UPDATES = 0;
UPDATE crs_orders SET status = 'p' WHERE status = 'Paid';
UPDATE crs_orders SET status = 'u' WHERE status = 'UnPaid';
SET SQL_SAFE_UPDATES = 1;