# Graduation project
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

## Content
Base path for this project : `localhost:8080/rest/`
#####[note]: NOTE:file with full collection of curl commands: "graduate_bush.postman_collection.json" is located in the root directory of the project

* [User API](#user-api)
    + [User Profile](#user-profile)
    + [User Vote](#user-vote)
    + [User Restaurant](#user-restaurant)
    + [User Menu](#user-menu)
    + [User Dish](#user-dish)
* [Admin API](#admin-api)
    + [Admin Profile](#admin-profile)
    + [Admin Vote](#admin-vote)
    + [Admin Restaurant](#admin-restaurant)
    + [Admin Menu](#admin-menu)
    + [Admin Dish](#admin-dish)

### User API

Authority: all authorized users (`/register` endpoint is available to unregistered users)

#### User Profile:
Base URL: `localhost:8080/rest/user`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/registration|POST|Registration|[look here][note]:|201|
|/{id}|GET|Get own profile|curl -w "\t%{http_code}\n" -s http://localhost:8080/rest/user --user newemail@ya.ru:newPassword|200|
|/|PUT|Update own profile|curl -w "\t%{http_code}\n" -s http://localhost:8080/rest/user -X PUT -H 'Content-Type:application/json;charset=UTF-8' --user newemail@ya.ru:newPassword -d '{"name":"New userName", "email":"newemail@yandex.ru", "password":"otherPassword"}'|204|
|/{id}|DELETE|Delete own profile|curl -w "\t%{http_code}\n" -s http://localhost:8080/rest/user -X DELETE --user newemail@yandex.ru:otherPassword|204|
<sub>[to table of content](#content)</sub>

#### User Vote:
Base URL: `localhost:8080/rest`

**NOTE:** *by default decision time is 11:00 AM*

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/votes?startDate=?endDate=?restaurantId=?userId=|GET|Get all votes filtered by date, restaurants, and users (each parameter can be null)|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes --user user@yandex.ru:password|200|
|/votes?restaurantId=?date=/|POST|To vote|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes/restaurant/100003 -X POST --user admin@gmail.com:admin|201|
|/votes/|PUT|Update own vote|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes/restaurant/100003 -X PUT --user user@yandex.ru:password|200|
|/votes|DELETE|Delete own vote|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes -X DELETE --user user@yandex.ru:password|422|

<sub>[to table of content](#content)</sub>

#### User Restaurant:
Base URL: `localhost:8080/rest/restaurants`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/|GET|Get restaurant list|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants --user user@yandex.ru:password|200|
|/name?name=|GET|Get restaurant by name|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants --user user@yandex.ru:password|200|

<sub>[to table of content](#content)</sub>

#### User Menu:
Base URL: `localhost:8080/rest/menus`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/?startDate=...&endDate=...&restaurantId=...|GET|Get filtered menu list (each param can be null)|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants --user user@yandex.ru:password|200|

<sub>[to table of content](#content)</sub>

#### User Dish:
Base URL: `localhost:8080/rest/dishes`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/?startDate=2020-07-02&endDate=2020-07-03&restaurantId=100003|GET|Get all dishes filtered by date and any restaurant|curl 'http://localhost:8080/dishes/?startDate=2020-07-02&endDate=2020-07-03&restaurantId=100003'|200|
|/?startDate=2020-07-02&endDate=2020-07-03|GET|Get all dishes filtered by date and not filtered by restaurants|curl 'http://localhost:8080/dishes/?startDate=2020-07-02&endDate=2020-07-03'|200|
|/name?name=dish 1|GET|Get dish by name|curl 'http://localhost:8080/dishes/name?name=dish 1'|200|
|/menu?id=100005|GET|Get dish by menu|curl 'http://localhost:8080/dishes/menu?id=100005'|200|
|/restaurant?id=...|GET|Get dish by restaurant|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants --user user@yandex.ru:password|200|

<sub>[to table of content](#content)</sub>

### Admin API:

Authority: admins only (who has role `ROLE_ADMIN`)

#### Admin Profile:
Base URL: `localhost:8080/rest/admin`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/|POST|Create new user|curl -s -X POST -d '{"name":"Admin1", "email":"admin1@gmail.com", "password":"admin1"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin --user admin@gmail.com:admin|201|
|/{id}|PUT|Update user profile|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/users/100000 -X PUT -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"Updated User name", "email":"newmail@yandex.ru", "password":"password"}'|200|
|/{id}|DELETE|Delete user profile|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/users/100000 -X DELETE --user admin@gmail.com:admin|204|
|/{id}|GET|Get user profile by ID|curl -s http://localhost:8080/rest/admin/100000 --user admin@gmail.com:admin|200|
|/|GET|Get all users|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/users --user admin@gmail.com:admin|200|
|/?email={email}|GET|Get user by email|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/users/by?email=admin@gmail.com --user admin@gmail.com:admin|200|

<sub>[to table of content](#content)</sub>

#### Admin Vote:
Base URL: `localhost:8080/rest/admin`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/votes/{id}|DELETE|Delete any vote|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/votes -X DELETE --user user@yandex.ru:password|422|

<sub>[to table of content](#content)</sub>

#### Admin Restaurant:
Base URL: `localhost:8080/rest/restaurants/admin`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/{id}|GET|Get restaurant by id|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants --user user@yandex.ru:password|200|
|/|POST|Add new restaurant|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants -X POST -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"newRestaurant"}'|201|
|/|PUT|Update restaurant|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100002 -X PUT -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"New restaurant name"}'|204|
|/{id}|DELETE|Delete restaurant by ID|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100002 -X DELETE --user admin@gmail.com:admin|204|

<sub>[to table of content](#content)</sub>

#### Admin Menu:
Base URL: `localhost:8080/rest/menus/admin`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/{id}|GET|Get menu by id|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants --user user@yandex.ru:password|200|
|/?date=...&restaurantId=...|POST|Add new menu of any restaurant|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants -X POST -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"newRes
|/|PUT|Update menu|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100002 -X PUT -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"New restaurant name"}'|204|
|/{id}|DELETE|Delete menu by ID|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100002 -X DELETE --user admin@gmail.com:admin|204|

<sub>[to table of content](#content)</sub>

#### Admin Dish:
Base URL: `localhost:8080/rest/dishes/admin`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/{id}|GET|Get dishes by id|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/restaurants --user user@yandex.ru:password|200|
|/?menuId=...|POST|Add new dish of any menu|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants -X POST -H 'Content-Type: application/json; charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"newRes
|/|PUT|Update dish|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100002 -X PUT -H 'Content-Type:application/json;charset=UTF-8' --user admin@gmail.com:admin -d '{"name":"New restaurant name"}'|204|
|/{id}|DELETE|Delete dish by ID|curl -w "\t%{http_code}\n" -s http://localhost:8080/lunch_vm/rest/admin/restaurants/100002 -X DELETE --user admin@gmail.com:admin|204|

<sub>[to table of content](#content)</sub>

