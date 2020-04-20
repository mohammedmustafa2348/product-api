Steps to Setup:
1. Clone the application
    
    	git clone https://github.com/mohammedmustafa2348/product-api.git

2. To view the database, use the link to view DB console:
        http://localhost:8080/h2-console
    	
	username: sa
	
    There is no password, it can be left empty. Please refer application.properties for more H2 DB properties. 

3. Build and run the app using maven 
	
    	mvn package
    	java -jar target/0.0.1-SNAPSHOT.jar
	
	b. Alternatively, you can run the app without packaging it using
	
	    mvn spring-boot:run
	    
 c.Using docker
 
        docker pull mustafaakram007/springboot-restapi-product-app
        docker run -p 8080:8080  mustafaakram007/springboot-restapi-product-app
        
   

   The app will start running at http://localhost:8080/product. 
    
   Rest APIs:
   
   
The app defines following CRUD APIs.

GET /product  - To get all products

GET /product/{id}  - To get a specific product

POST /product - To save a product

PUT /product/{id} - To update a product;  

DELETE /delete/{id} - To delete a product

DELETE /delete/ - To delete all

A sample request body to use for Save and Update operation:    

    {
      "type": "phone",
      "price": 277.0,
      "address": "Blake gränden,  Karlskrona",
      "properties": "color:blue"
    }



Task:

In a basic Dockerized Springboot Maven application, build a single REST API endpoint that returns a filtered set of products from the provided data in the data.csv file

GET /product

Query Parameter			Description

type    					The product type. (String. Can be 'phone' or 'subscription')

min_price   				The minimum price in SEK. (Number)

max_price   				The maximum price in SEK. (Number)

city    					The city in which a store is located. (String)

property    				The name of the property. (String. Can be 'color' or 'gb_limit')

property:color  			The color of the phone. (String)

property:gb_limit_min    	The minimum GB limit of the subscription. (Number)

property:gb_limit_max    	The maximum GB limit of the subscription. (Number)


The expected response is a JSON array with the products in a 'data' wrapper. 


Example: GET /product?type=subscription&max_price=1000&city=Stockholm

    {
        data: [ 
            {
                type: 'subscription',
                properties: 'gb_limit:10',
                price: '704.00',
                store_address: 'Dana gärdet, Stockholm'
            },
            {
                type: 'subscription',
                properties: 'gb_limit:10',
                price: '200.00',
                store_address: 'Octavia gränden, Stockholm'
            }
        ]
    }

Your solution should correctly filter any combination of API parameters and use some kind of a datastore.
All parameters are optional, all minimum and maximum fields should be inclusive (e.g. min_price=100&max_price=1000 should return items with price 100, 200... or 1000 SEK). 
The applications does not need to support multiple values (e.g. type=phone,subscription or property.color=green,red).

We should be able to:
- build the application with Maven
- build the Docker image and run it
- make requests to verify the behavior
