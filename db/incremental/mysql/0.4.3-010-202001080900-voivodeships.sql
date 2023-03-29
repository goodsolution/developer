CREATE TABLE voivodeships (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(500) NOT NULL,
PRIMARY KEY (id)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

alter table city add column voivodeship_id INT;
CREATE INDEX idx_voivodeship_id ON city(voivodeship_id);

insert into voivodeships (name)
values('dolnośląskie');
insert into voivodeships (name)
values('kujawsko-pomorskie');
insert into voivodeships (name)
values('lubelskie');
insert into voivodeships (name)
values('lubuskie');
insert into voivodeships (name)
values('łódzkie');
insert into voivodeships (name)
values('małopolskie');
insert into voivodeships (name)
values('mazowieckie');
insert into voivodeships (name)
values('opolskie');
insert into voivodeships (name)
values('podkarpackie');
insert into voivodeships (name)
values('podlaskie');
insert into voivodeships (name)
values('pomorskie');
insert into voivodeships (name)
values('śląskie');
insert into voivodeships (name)
values('świętokrzyskie');
insert into voivodeships (name)
values('warmińsko-mazurskie');
insert into voivodeships (name)
values('wielkopolskie');
insert into voivodeships (name)
values('zachodniopomorskie');



