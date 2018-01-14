#Lunch
===============================
##REST API для голосования за меню ресторана.

##В API предусмотрены следующие обьекты:  
User - пользователь  
Restaurant - ресторан  
Menu - меню ресторана за один из дней  
Dish - блюдо в меню.  
Vote - голосование за один из ресторанов в какой-то день.  

Предусмотрено 2 роли пользователей API: ADMIN_ROLE и USER_ROLE.  
Пользователи с ADMIN_ROLE(администраторы) имеют функционал по наполнению системы данными:  
-заведение и редактирование ресторанов.  
-заведение, замена и удаление меню ресторанов.  

Пользователи с USER_ROLE(клиенты) имеют право голосовать.  

Получение списков ресторанов и меню за текущий день имеют все зарегистрированные пользователи.  
Список меню за любой день могут получить только администраторы.  

##Список функций:

###Регистрация:  
POST  /users/register  
curl  -i -X POST -H "Content-Type:application/json;charset=UTF-8" -d "{\"name\":\"NewUser\",\"email\":\"new@mail.ru\",\"role\":\"ROLE_USER\",\"password\":\"newPass\"}"   http://localhost:8080/lunch/users/register  
Доступна для незарегистрированных пользователей. Регистрируем только клиентов.
Заведение администраторов в API не предусмотрено.

###Получение своих данных:  
GET /users  
curl -i http://localhost:8080/lunch/users -u admin@mail.ru:aPassword  
Доступно для всех зарегистрированных пользователей.

###Получение списка ресторанов:  
GET /restaurants  
curl  -i http://localhost:8080/lunch/restaurants -u user@mail.ru:uPassword  
Доступно для всех зарегистрированных пользователей.  

###Получение данных одного ресторана:  
GET /restaurants/{restaurant id}  
curl  -i http://localhost:8080/lunch/restaurants/100005 -u admin@mail.ru:aPassword  
Доступно для всех зарегистрированных пользователей.

###Заведение и изменение ресторана:  
POST /restaurants/admin  
curl  -i -X POST -H "Content-Type:application/json;charset=UTF-8" -d "{\"name\":\"Cafe\"}" http://localhost:8080/lunch/restaurants/admin -u admin@mail.ru:aPassword  
Доступно только администратору

###Получение списка меню:  
GET /restaurants/menus  
curl  -i http://localhost:8080/lunch/restaurants/menus -u user@mail.ru:uPassword  
За текущий день. Достуно всем зарегистрированным.  
GET /restaurants/menus?date={yyyy-mm-dd}  
curl  -i http://localhost:8080/lunch/restaurants/menus/admin?date=2018-01-13 -u admin@mail.ru:aPassword  
За любой день. Достуно администраторам.

###Получение меню определенного ресторана:  
GET /restaurants/{restaurant id}/menus  
curl  -i http://localhost:8080/lunch/restaurants/100004/menus -u user@mail.ru:uPassword  
За текущий день. Достуно всем зарегистрированным.  
GET /restaurants/{restaurant id}/menus?date={yyyy-mm-dd}  
curl  -i http://localhost:8080/lunch/restaurants/100003/menus/admin?date=2018-01-13 -u admin@mail.ru:aPassword  
За любой день. Достуно администраторам.

###Заведение и замена меню:  
POST /restaurants/menus/admin  
curl  -i -X POST  -H "Content-Type:application/json;charset=UTF-8" -d "{\"dishes\":[{\"name\":\"dish1\",\"cost\":22000},{\"name\":\"dish2\",\"cost\":6000},{\"name\":\"cola\",\"cost\":5000}],\"date\":\"2018-01-16\",\"restaurant\":{\"id\":100003}}" http://localhost:8080/lunch/restaurants/menus/admin -u admin@mail.ru:aPassword  
Достуно администраторам. Если у меню указан id, то существующее удаляется и создается новое. Возможно только для даты более текущей.

###Удаление меню:  
DELETE /restaurants/menus/{menu id}/admin  
curl  -i -X DELETE http://localhost:8080/lunch/restaurants/menus/100025/admin -u admin@mail.ru:aPassword  
Достуно администраторам.Возможно только для даты более текущей.

###Голосование:  
POST /users/vote/{restaurant id}  
curl  -i -X POST -H "Content-Type:application/json;charset=UTF-8"  http://localhost:8080/lunch/users/vote/100003 -u expert@mail.ru:ePassword  
Только для клиентов. Возможно изменить голосование до 11 часов.

###Посмотреть свое голосование сегодня:  
GET /users/vote  
curl -i http://localhost:8080/lunch/users/vote -u user@mail.ru:uPassword  
Только для клиентов.
