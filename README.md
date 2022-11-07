# Document

## Build & Run

- ./gradlew clean build - To build project. It will generate jar file to build/libs folder.
- ./gradlew bootRun --args='C:\Users\pratik-pc\Downloads\data1.json' : To run application
- Find Suggestion postman collection file and import to postman.

## Assumption & Explanation

- Passing DB file to args rather than properties because If want to run two instance with different DB it will work. Or
  Can change DB in Prod with deployment.
- support utf-8 character only
- Loading Data at staring of application.
- To load json data used BulkInsertCity Entity, So that it will not fire select query before inserting as assuming no
  duplicate record in json.
- In input only character and coma is allowed. Number and other special char will give Bad Request.
- Aop is used only for logging purpose.
- Limit of 5 record is Configurable in Application properties

## Search

- Read CitySearchService:citySearch method comment. Different scenarios. 
