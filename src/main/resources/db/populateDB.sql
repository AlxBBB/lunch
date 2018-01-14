
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, role) VALUES
  ('Посетитель', 'user@mail.ru', '$2a$10$3IV47u8r8/1rckRkoFa20e27KDLspubVUadlUNAsxoV.23uXLf9Vm', 'ROLE_USER'), --100000   uPassword
  ('Ценитель', 'expert@mail.ru', '$2a$10$QxP8rEUBNGYL/KGRW3KRneHYekbAd.h84uHqRQbdoSIQc1Alc0nO6', 'ROLE_USER'), --100001   ePassword
  ('Админ', 'admin@mail.ru', '$2a$10$6IrxQ86A6qf4ke.5hmOc4OYzSdSROyb4gQpklli8fpVfJsiyog8q2', 'ROLE_ADMIN');    --100002   aPassword

INSERT INTO restaurants (name) VALUES
  ('Биг Мак'),                                              --100003
  ('Дак гриль'),                                            --100004
  ('Пельмени');                                             --100005

INSERT INTO menus (restaurant_id,date) VALUES
  (100003,date_sub(today(),INTERVAL 1 DAY )),               --100006
  (100005,date_sub(today(),INTERVAL 1 DAY )),               --100007
  (100003,today()),                                         --100008
  (100004,today());                                         --100009


INSERT INTO votes (user_id, restaurant_id, date) VALUES
  (100000,100003,date_sub(today(),INTERVAL 1 DAY)),         --1000010
  (100000,100003,today());                                  --1000011


INSERT INTO dishes (name, menu_id, cost) VALUES
  ('Картошка фри',100006,7000),                             --1000012
  ('Чизбургер',100006,12000),                               --1000013
  ('Кола',100006,5000),                                     --1000014
  ('Картошка фри',100008,7000),                             --1000015
  ('Гамбургер',100008,12500),                               --1000016
  ('Фанта',100008,5000),                                    --1000017
  ('Пельмешки',100007,11000),                               --1000018
  ('Бульен',100007,2000),                                   --1000019
  ('Чай элитный',100007,5000),                              --1000020
  ('Яичница',100009,17000),                                 --1000021
  ('Утка по Пекински',100009,32000),                        --1000022
  ('Яйцо вареное',100009,8000),                             --1000023
  ('Какао',100009,8500);                                    --1000024


INSERT INTO menus (restaurant_id,date) VALUES
  (100003,date_add(today(),INTERVAL 1 DAY ));               --100025


