drop schema public cascade;
create schema if not exists public;

create table role
(
  id         serial primary key,
  role       varchar(100),
  created_at timestamp,
  updated_at timestamp
);

create table account
(
  id               serial primary key,
  name             varchar(100),
  email            varchar(100),
  enabled          boolean,
  encoded_password varchar(200),
  created_at       timestamp,
  updated_at       timestamp
);


create table account_role
(
  account_id int,
  role_id    int,
  constraint fk_account_id
    foreign key (account_id)
      references account (id),
  constraint fk_role_id
    foreign key (role_id)
      references role (id)
);


create table verification_token
(
  token_id          serial primary key,
  account_id  integer,
  token       varchar(200),
  expiry_date timestamp,
  constraint fk_account_id
    foreign key (account_id)
      references account (id)

);
/*
создает таблицы для бд.
код для PostgreSQL
для MySql айди задавать так :
id integer not null primary key auto increment
*/
