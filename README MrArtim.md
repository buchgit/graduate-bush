# Выпуская работа в рамках онлайн проекта <a href="https://github.com/JavaOPs/topjava">Topjava</a>
## Постановка задачи
Спроектировать и реализовать REST API, используя Hibernate/Spring/SpringMVC (или Spring-Boot) **без пользовательского интерфейса**.

Задача:

Создать систему голосования для выбора куда сходить на обед.
* 2 типа пользователей: Администраторы (Админы, admin) или обычные пользователи
* Админ может добавлять рестораны и их меню на день (обычно 2-5 позиции, название блюда и цену)
* Меню меняется каждый день (Админы вносят все изменения)
* Пользователи могут голосовать за то, в какой рестора пойти обедать
* Голос учитывается только 1 раз
* Если пользователь голосует повторно в этот день, то:
  - Если это произошло до 11:00, считаем, что он изменил свое решение
  - Если после 11:00 - слишком поздно и изменение не может быть принято
  
 Ресторан предоставляет новое меню каждый день.
 
 В качестве результата, предоставьте ссылку на GitHub репозиторий. Он должен содержать код, README.md с документацией по API и примерами curl команд для тестирования.

## Документация
### Описание API
#### Сущности
* Пользователь (**User**): id, name, email, password, enabled, registered, roles; **UserTo**: id, name, email, password
* Ресторан (**Restaurant**): id, name; dishes
* Блюдо ресторана (**Dish**): id, name, date, price; **DishTo**: id, name, price

#### Формат описания запроса
<Тип_запроса> <URI> [Параметры_запроса] [(Тело_запроса)] [: <Данные_ответа>]

Для всех запросов, кроме регистрации необходима авторизация. Поддерживается базовая авторизация: email, password

#### Для пользователей
* Регистрация: Post /api/register (UserTo): User
* Данные о пользователе: Get /api/profile : UserTo
* Изменить данные: Put /api/profile (UserTo)
* Удалить данные: Delete /api/profile
* Список ресторанов: Get /api/restaurants ?withDishes (default=true) : список Restaurant
    - Результат запроса кешируется на 12 часов
* Данные о ресторане: Get /api/restaurants/id ?withDishes (default=true): Restaurant
* Все блюда ресторана: Get /api/restaurant/id/dishes: список Dish
* Проголосовать: Post /api/restaurant/id/vote

#### Для администратора
##### Работа с пользователями
* Список: Get /api/admin/users: список User
* Данные об одном: Get /api/admin/users/id: User
* Создать: Post /api/admin/users (UserTo): User
* Изменить: Put /api/admin/users/id (UserTo)
* Удалить: Delete /api/admin/users/id

##### Работа с ресторанами
* Список: Get /api/admin/restaurants: список Restaurant
    - Результат запроса кешируется
* Данные об одном: Get /api/admin/restaurants/id: Restaurant
* Создать: Post /api/admin/restaurants (Restaurant): Restaurant
* Изменить: Put /api/admin/restaurants/id (Restaurant)
* Удалить: Delete /api/admin/restaurants/id

##### Работа с меню ресторана
* Список: Get /api/admin/restaurants/restaurantId/dishes: список Dish
* Данные об одном блюде: Get /api/admin/restaurants/restaurantId/dishes/id: Dish
* Создать: Post /api/admin/restaurants/restaurantId/dishes (DishTo): Dish
* Изменить: Put /api/admin/restaurants/restaurantId/dishes/id (DishTo)
* Удалить: Delete /api/admin/restaurants/restaurantId/dishes/id

### Примеры команд (для приложения, развернутого в контексте topjava_graduation)
#### User registration
`curl -s -X POST -d '{"name" : "Registered User", "email" : "registered@world.org", "password" : "reguser"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/api/register`
#### User profile
`curl -s http://localhost:8080/topjava_graduation/api/profile --user user@world.org:user`
#### User update profile
`curl -s -X PUT -d '{"id":100000,"name":"Updated User","email":"user@world.org","password":"user"}' -H 'Content-Type:application/json' http://localhost:8080/topjava_graduation/api/ --user user@world.org:user`
#### User get restaurants with dishes
`curl -s http://localhost:8080/topjava_graduation/api/restaurants --user user@world.org:user`
#### User vote
`curl -s -X POST http://localhost:8080/topjava_graduation/api/restaurants/100002/vote --user user@world.org:user`
#### Admin get users
`curl -s http://localhost:8080/topjava_graduation/api/admin/users --user admin@world.org:admin`
#### Admin get user
`curl -s http://localhost:8080/topjava_graduation/api/admin/users/100000 --user admin@world.org:admin`
#### Admin create user
`curl -s -X POST -d '{"name" : "New User", "email" : "newuser@world.org", "password" : "newuser", "roles" : ["USER"]}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/api/ --user admin@world.org:admin`
#### Admin update user
`curl -s -X PUT -d '{"id":100000,"name":"Updated User","email":"user@world.org","password":"user","registered":"2017-05-20T05:06:09.711+0000","enabled":false,"roles":["USER"]}' -H 'Content-Type:application/json' http://localhost:8080/topjava_graduation/api/ --user admin@world.org:admin`
#### Admin delete user
`curl -s -X DELETE http://localhost:8080/topjava_graduation/api/ --user admin@world.org:admin`
#### Admin get restaurants
`curl -s http://localhost:8080/topjava_graduation/api/admin/restaurants --user admin@world.org:admin`
#### Admin get restaurant
`curl -s http://localhost:8080/topjava_graduation/api/admin/restaurants/100002 --user admin@world.org:admin`
#### Admin create restaurant
`curl -s -X POST -d '{"name" : "Friends and Family"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/api/admin/restaurants --user admin@world.org:admin`
#### Admin update restaurant
`curl -s -X PUT -d '{"id" : "100002", "name" : "Не прага"}' -H 'Content-Type:application/json' http://localhost:8080/topjava_graduation/api/admin/restaurants/100002 --user admin@world.org:admin`
#### Admin delete restaurant
`curl -s -X DELETE http://localhost:8080/topjava_graduation/api/admin/restaurants/100002 --user admin@world.org:admin`
#### Admin get restuarant dishes
`curl -s http://localhost:8080/topjava_graduation/api/admin/restaurants/100002/dishes --user admin@world.org:admin`
#### Admin get dish
`curl -s http://localhost:8080/topjava_graduation/api/admin/restaurants/100002/dishes/100004 --user admin@world.org:admin`
#### Admin create dish
`curl -s -X POST -d '{"name" : "Toast", "price" : 99}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava_graduation/api/admin/restaurants/100002/dishes --user admin@world.org:admin`
#### Admin update dish
`curl -s -X PUT -d '{"id" : "100004", "name" : "Dorado", "price" : "199"}' -H 'Content-Type:application/json' http://localhost:8080/topjava_graduation/api/admin/restaurants/100002/dishes/100004 --user admin@world.org:admin`
#### Admin delete dish
`curl -s -X DELETE http://localhost:8080/topjava_graduation/api/admin/restaurants/100002/dishes/100004 --user admin@world.org:admin`

## Используемые инструменты и технологии
* Maven
* Java 8
* Spring (Data JPA, MVC, Security, Test, Security test)
* Hibernate
* SLF4J, Logback
* HSQLDB
* JUnit
* Json (Jackson)
* EhCache
* jsoup
