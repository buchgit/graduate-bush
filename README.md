[![Codacy Badge](https://app.codacy.com/project/badge/Grade/956581f626de453fa959b789e814b09b)](https://www.codacy.com/manual/buchgit/graduate-bush?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=buchgit/graduate-bush&amp;utm_campaign=Badge_Grade)
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
<br>
###### <a name="note"> NOTE: file with full collection of curl commands: "graduate_bush.postman_collection.json" is located in the root directory of the project</a>

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

#### User Profile:
Base URL: `localhost:8080/rest/user`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/registration|POST|Registration|[look note](#note)|201|
|/{id}|GET|Get own profile|curl -s http://localhost:8080/rest/user --user user@gmail.com:user|200|
|/|PUT|Update own profile|[look note](#note)|204|
|/{id}|DELETE|Delete own profile|curl -s -X DELETE http://localhost:8080/rest/user/100000 --user user@gmail.com:user|204|

<sub>[to table of content](#content)</sub>

#### User Vote:
Base URL: `localhost:8080/rest/votes`

**NOTE:** *by default decision time is 11:00 AM*

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|?startDate=...?endDate=...?restaurantId=...?userId=|GET|Get all votes filtered by date, restaurants, and users (each parameter can be null)|[look note](#note)|200|
|?restaurantId=...?date=.../|POST|To vote|[look note](#note)|201|
|/|PUT|Update own vote|[look note](#note)|200|
|//{id}|DELETE|Delete own vote|[look note](#note)|204|

<sub>[to table of content](#content)</sub>

#### User Restaurant:
Base URL: `localhost:8080/rest/restaurants`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/|GET|Get restaurant list|[look note](#note)|200|
|/name?name=...|GET|Get restaurant by name|[look note](#note)|200|

<sub>[to table of content](#content)</sub>

#### User Menu:
Base URL: `localhost:8080/rest/menus`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/?startDate=...&endDate=...&restaurantId=...|GET|Get filtered menu list (each param can be null)|[look note](#note)|200|

<sub>[to table of content](#content)</sub>

#### User Dish:
Base URL: `localhost:8080/rest/dishes`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/?startDate=...&endDate=...&restaurantId=...|GET|Get all dishes filtered by date and any restaurant|[look note](#note)|200|
|/?startDate=...&endDate=...|GET|Get all dishes filtered by date and not filtered by restaurants|[look note](#note)|200|
|/name?name=...|GET|Get dish by name|[look note](#note)|200|
|/menu?id=...|GET|Get dish by menu|curl -s http://localhost:8080/rest/dishes/menu?id=100005 --user user@gmail.com:user|200|

<sub>[to table of content](#content)</sub>

### Admin API:

Authority: admins only (who has role `ROLE_ADMIN`)

#### Admin Profile:
Base URL: `localhost:8080/rest/admin`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/|POST|Create new user|[look note](#note)|201|
|/{id}|PUT|Update user profile|[look note](#note)|200|
|/{id}|DELETE|Delete user profile|[look note](#note)|204|
|/{id}|GET|Get user profile by ID|curl -s http://localhost:8080/rest/admin/100000 --user admin@gmail.com:admin|200|
|/|GET|Get all users|[look note](#note)|200|
|/?email=...|GET|Get user by email|[look note](#note)|200|
|/{id}?enabled=...|POST|Set user enabled true/false|[look note](#note)|200|

<sub>[to table of content](#content)</sub>

#### Admin Vote:
Base URL: `localhost:8080/rest/votes/admin`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/{id}|DELETE|Delete any vote|[look note](#note)|422|

<sub>[to table of content](#content)</sub>

#### Admin Restaurant:
Base URL: `localhost:8080/rest/restaurants/admin`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/{id}|GET|Get restaurant by id|[look note](#note)|200|
|/|POST|Add new restaurant|[look note](#note)|201|
|/|PUT|Update restaurant|[look note](#note)|204|
|/{id}|DELETE|Delete restaurant by ID|[look note](#note)|204|

<sub>[to table of content](#content)</sub>

#### Admin Menu:
Base URL: `localhost:8080/rest/menus/admin`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/{id}|GET|Get menu by id|[look note](#note)|200|
|/?date=...&restaurantId=...|POST|Add new menu of any restaurant|[look note](#note)|201|
|/|PUT|Update menu|[look note](#note)|204|
|/{id}|DELETE|Delete menu by ID|[look note](#note)|204|

<sub>[to table of content](#content)</sub>

#### Admin Dish:
Base URL: `localhost:8080/rest/dishes/admin`

|URL|HTTP method|Description|Curl|Response Code (success)|
|---|:---:|---|---|:---:|
|/{id}|GET|Get dishes by id|[look note](#note)|200|
|/?menuId=...|POST|Add new dish of any menu|[look note](#note)|201|
|/|PUT|Update dish|[look note](#note)|204|
|/{id}|DELETE|Delete dish by ID|[look note](#note)|204|

<sub>[to table of content](#content)</sub>


