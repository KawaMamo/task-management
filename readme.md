# Task Management System
This repository is only the business logic for the Task Management System which consists of the following services:
- [Task Management service](https://github.com/KawaMamo/task-management.git)
- [Identity Provider service](https://github.com/KawaMamo/Identity-provider-TM.git)
- Discovery service
- [API Gateway service](https://github.com/KawaMamo/tm-gateway.git)
- Identity Provider DB
- Business DB
## Usage
you only need to run the following command while you are in same directory as the compose.yaml file:
```bash
 docker network create -d bridge my-bridge
 docker compose up
 ```
## System Architecture
Microservices architecture is followed to build the System.

## Authentication
JWT is used to authenticate the users and this is the responsibility of Identity Provider Service.
Other services can verify the token using the public key provided by the Identity provider. Registering new users is done through the business service
which add the user information through the Identity Provider and get the reference to the user to keep it persisted to Business DB along with the other user's information.

## API Gateway service
Api gateway get service's url from Discovery service and redirect the requests to the services. so all requests meant to go to the identity provider should start with "/ip" 
while all the requests that should go to the business service should start with "/tm".

## Swagger 
- springdoc.swagger-ui.path= /api/v1/pub/swagger-ui.html
- springdoc.api-docs.path= /api/v1/pub/api-docs
- since the api uses Authorization header for authentication you need first to login and then send the jwt token with all the other requests. and it is recommended to use postman for testing.
