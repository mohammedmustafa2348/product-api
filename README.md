Steps to Setup
1. Clone the application



2. To view the database
Use the link to view DB console:
http://localhost:8080/h2-console

    username: sa

    There is no password, it can be left empty. 

    Please refer application.properties for more H2 DB properties. 

2. Build and run the app using maven

    mvn package

    java -jar target/0.0.1-SNAPSHOT.jar

    Alternatively, you can run the app without packaging it using -

    mvn spring-boot:run
    
    The app will start running at http://localhost:8080/

Explore Rest APIs
The app defines following CRUD APIs.

GET /product  - To get all products

GET /product/{id}  - To get a specific product

POST /product - To save a product

PUT /product/{id} - To update a product

DELETE /delete/{id} - To delete a product

DELETE /delete/ - To delete all