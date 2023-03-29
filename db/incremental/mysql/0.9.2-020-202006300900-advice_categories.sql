alter table advice_categories add price_per_month decimal(14,2) DEFAULT 0.0 NOT NULL;
alter table advice_categories add price_per_quarter decimal(14,2) DEFAULT 0.0 NOT NULL;
alter table advice_categories add price_per_half_year decimal(14,2) DEFAULT 0.0 NOT NULL;
alter table advice_categories add price_per_year decimal(14,2) DEFAULT 0.0 NOT NULL;

alter table advice_categories_used add period VARCHAR(1);


