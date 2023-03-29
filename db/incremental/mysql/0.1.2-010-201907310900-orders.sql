DELIMITER //

CREATE TRIGGER ai_order_date
BEFORE INSERT
   ON orders FOR EACH ROW

BEGIN

   SET new.order_date = new.order_datatime;

END;//