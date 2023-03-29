ALTER TABLE crs_sent_emails MODIFY title varchar(500);
ALTER TABLE crs_sent_emails MODIFY content text(65000);
ALTER TABLE crs_sent_emails MODIFY event varchar(5);