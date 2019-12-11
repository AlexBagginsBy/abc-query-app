Description.

1. What is done...
The app idea is implemented using com.baggins.abcqueryapp.controllers.EmployeeController.getAllByHireDateRange method as an example.
(feel free to check it via http://localhost:8090/employees/hired?hiredFrom=1995-01-01&hiredBefore=1996-01-01)
I'd like to call it "a biking skeleton".

The task dropped me into a number of unfamiliar areas (SpringBoot, performance testing), so the result look to me as reinvented 4.5-wheel-bicycle. 
To measure queries performance I've used Apache jMeter.  
Currently it requires a separate manual launch and is not bonded with application work or build flows. 
SQL queries versions are stored in separate .sql files used by both application backend and jMeter tests.  

To summarize the main idea: 
- the application supports possibility to keep and manage multiple SQL query version under one REST endpoint;
- performance testing should be performed separately via jMeter to check SQl optimization efficiency;


2. ... and still to be done.
- unit tests
- reloadable properties

3. Requirements:
- install MySql DB
- create schema named `employees_schema` (it should be filled with 50k rows on startup)
- install Apache jMeter https://jmeter.apache.org/download_jmeter.cgi
- put MySql Connector/J into jMeter's .../lib/ folder https://dev.mysql.com/downloads/connector/j/5.1.html
- build & run the app. the homepage is http://localhost:8090/employees
