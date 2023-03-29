CREATE INDEX idx_payment_id ON orders(payment_id);

alter table orders ADD COLUMN status_change_source VARCHAR(4);

alter table orders MODIFY COLUMN status_change_source VARCHAR(4) COMMENT 'S-updated by scheduler';


INSERT INTO users(
   username,
   password,
   role_id,
   insert_date,
   update_date,
   modified_type,
   modified_by,
   status,
   failed_logins,
   name,
   surname,
   last_password_change,
   e_mail,
   driver_id,
   mobile
)
SELECT
   'Automat',
   password,
   role_id,
   insert_date,
   update_date,
   modified_type,
   modified_by,
   status,
   failed_logins,
   'Automat',
   'Automat',
   last_password_change,
   e_mail,
   driver_id,
   mobile
FROM
   users
WHERE
   username = 'Pawel' and name='Pawel';