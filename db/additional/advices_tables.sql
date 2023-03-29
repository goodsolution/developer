insert into advices (
app_id,
domain,
content,
type,
scope,
action,
data_type,
component,
class,
name
) values (
'masterdieta-panel',
'panel-user',
'content',
'P',
'G',
NULL,
NULL,
NULL,
'EveryFourthSecondAdvice',
'Test advice'
);


insert into advices (
app_id,
domain,
content,
type,
scope,
action,
data_type,
component,
class,
name
) values (
'masterdieta-panel',
'panel-user',
'content',
'P',
'G',
NULL,
NULL,
NULL,
'EverySecondMinuteAdvice',
'Test advice2'
);

insert into advices (
app_id,
domain,
content,
type,
scope,
action,
data_type,
component,
class,
name
) values (
'masterdieta-panel',
'panel-user',
'Text to be displayed',
'P',
'G',
NULL,
NULL,
NULL,
'JustDisplayAdvice',
'Text to be displayed'
);

insert into advices (
app_id,
domain,
content,
type,
scope,
action,
data_type,
component,
class,
name,
variable_name
) values (
'masterdieta-panel',
'panel-user',
'What is your first name',
'Q',
'G',
NULL,
'STRING',
'INPUT_TEXT',
'JustDisplayAdvice',
'Question to be displayed',
'first_name'
);


insert into advices (
app_id,
domain,
content,
type,
scope,
action,
data_type,
component,
class,
name,
variable_name
) values (
'masterdieta-panel',
'panel-user',
'Do you prefer cats or dogs',
'Q',
'G',
NULL,
'STRING',
'SELECT',
'JustDisplayAdvice',
'Question with select to be displayed',
'cat_dog_preference'
);

insert into advice_answer_options (
    advice_id,
    option_name,
    value,
    option_order
) values (
    (select id from advices where name = 'Question with select to be displayed'),
    'Cat',
    'C',
    1
);

insert into advice_answer_options (
    advice_id,
    option_name,
    value,
    option_order
) values (
    (select id from advices where name = 'Question with select to be displayed'),
    'Dog',
    'D',
    2
);


-----------------------------------------------------------------------

insert into applications(
    application_id,
    description,
    secret_key
) values (
    'life-adviser',
    'Life adviser',
    '2b2221ed-43d3-4834-8bd9-a0454f17037f-1592039601774-718d6284-2250-4af7-b700-2cd067e0cc83'
);



insert into advice_categories (
    application_id,
    context,
    name,
    description,
    tags,
    price,
    period_code
) values (
'life-adviser',
'main-stream',
'Java links',
'Interesting links regarding java programming language',
'java; programming; links; link',
0.0,
'M'
);

insert into advice_categories (
    application_id,
    context,
    name,
    description,
    tags,
    price,
    period_code
) values (
'life-adviser',
'main-stream',
'General',
'General info free for all',
'',
0.0,
'M'
);

insert into advice_categories (
    application_id,
    context,
    name,
    description,
    tags,
    price,
    period_code
) values (
'life-adviser',
'main-stream',
'Quotes - Money',
'Quotes - Money',
'quote; quotes; money',
5.0,
'Y'
);

insert into advices (
app_id,
domain,
content,
type,
scope,
action,
data_type,
component,
class,
name,
category_id
) values (
'life-adviser',
'main-stream',
'https://en.wikipedia.org/wiki/Java_(programming_language)',
'P',
'G',
NULL,
NULL,
NULL,
'JustDisplayAdvice',
'Java Link',
(select id from advice_categories where name = 'Java links')
);

insert into advice_categories_used (
    application_id,
    context,
    context_id,
    date_from,
    date_to,
    price,
    currency_code,
    category_id
) values (
    'life-adviser',
    'main-stream',
    '2',
    NOW(),
    '2040-01-01',
    0.0,
    'PLN',
    (select id from advice_categories where name = 'Java links')
);

insert into accounts(
    name,
    preferred_hour_from,
    preferred_hour_to,
    all_day,
    type
) values (
    'Test',
    6,
    23,
    0,
    'L'
);

update users
set account_id = (select id from accounts where name = 'Test')
where username= 'Pawel';

insert into users_roles (name) values ('LIFE-ADVISER');

-----==================================================
-----==================================================

insert into applications(
    application_id,
    description,
    secret_key
) values (
    'inner-voice',
    'inner-voice',
    '2b2221ed-43d3-4834-8bd9-a0454f17037f-1592039601774-718d6284-2250-4af7-b700-2cd067e0cc83'
);

insert into advices(
app_id,
context,
content,
type,
title,
variable_name,
execution_condition,
combo_json,
status
) values (
'inner-voice',
'inner-voice',
'Witaj w Inner Voice',
'P',
'Inner voice komunikat powitalny',
null,
'true',
null,
'A'
);

insert into advice_categories (
    application_id,
    context,
    name,
    description
) values (
'inner-voice',
'inner-voice',
'Test',
'Test desc'
);

insert into advice_categories_used (
    customer_id,
    category_id
) values (
    1,
    (select id from advice_categories where name = 'Test')
);

insert into  triggered_advices(
             advice_id,
             customer_id,
             app_id,
             context,
             context_id,
             title,
             content,
             type,
             lang,
             data_type,
             status,
             variable_name,
             execution_condition,
             combo_json,
             create_datetime,
             trigger_datetime
) values (
1,
1,
'inner-voice',
'inner-voice',
null,
'Komunikat powitalny',
'Inner voice wita',
'P',
'pl',
'STRING',
'T',
null,
'true',
null,
NOW(),
null
);

