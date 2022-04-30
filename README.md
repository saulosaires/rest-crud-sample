# Read Me

this is a sample project to show how to do a simple CRUD with Spring boot

# Getting Started

for the sake of testing we are using the H2 in memory and we can check the console from this link:
http://localhost:8080/h2-console

#### We can test the endpoints with [Postman](https://https://www.postman.com/), some sample urls are found just below

[GET] http://localhost:8080/user/1

[POST] http://localhost:8080/user

body:
{"name":"Dom Pedro I", "birth" :"18/12/1986"}

[PUT] http://localhost:8080/user/1

body:
{"name":"Dom Pedro I de braganca", "birth" :"18/12/1986"}

[PATCH] http://localhost:8080/user/1

body:
{"name":"Dom Pedro II de braganca"}

[DELETE] http://localhost:8080/user/1



