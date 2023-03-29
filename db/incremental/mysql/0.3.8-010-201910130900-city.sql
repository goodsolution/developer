DELIMITER $$
CREATE PROCEDURE `update_cities_order` ()
BEGIN
  DECLARE bDone INT;
  DECLARE co INT;
  DECLARE c_id INT;
  DECLARE curs CURSOR FOR SELECT city_id FROM city WHERE country = 'PL' and active = '1' order by city COLLATE `utf8_polish_ci`;
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET bDone = 1;
  OPEN curs;

  SET bDone = 0;
  SET co = 1;
  REPEAT
    FETCH curs INTO c_id;
       update city set `order` = co where city_id = c_id;
       SET co = co + 1;
  UNTIL bDone END REPEAT;

  CLOSE curs;
  COMMIT;
END$$






