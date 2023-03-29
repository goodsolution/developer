update users set e_mail = id where e_mail = '';
create unique index idx_email on users (e_mail);