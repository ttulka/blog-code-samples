# Monolithic Objects

Example code to show differences between monolithic object design and behavior-driven object design.

Java 11, Spring Boot 2.3

## Structure

The project source code contains two independent applications with exactly same API and functionality.

- `..bad`
    - Contains code for the monolithic approach. 
- `..good`
    - Contains code for the behavior-driven approach.

## Domain

The domain is a product management. API contains endpoints for listing and finding products and editing product title and price.

## REST Endpoints

- `GET /product`
    - List all products.
- `GET /product/1`
    - Finds the product with ID 1.
- `PUT /product/1/title`
    - Changes the title of the product with ID 1.
- `PUT /product/1/price`
    - Changes the price of the product with ID 1.
    
### Examples
Changes the title to "New Title" for the product with ID 1:
```
curl http://localhost:8080/product/1/title \
    -X PUT \
    -H "Content-Type: text/plain" \
    --data "New Title"
```

## Run
Run the monolithic or the behavior-driven application respectively:
```
mvn spring-boot:run \
    -Dspring-boot.run.main-class=com.ttulka.blog.samples.bad.BadApplication
```
```
mvn spring-boot:run \
    -Dspring-boot.run.main-class=com.ttulka.blog.samples.good.GoodApplication
```