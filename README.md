# FitnessStudioMicro

Fitness Studio is a microservices-based web application for managing a healthy fitness diet. It is built using the following technologies:

* Spring Boot
* Spring MVC
* Spring Data
* Spring Security with JWT
* Spring Cloud
* Spring Mail
* JPA with Hibernate implementation
* JobRunr for asynchronous tasks
* PostgreSQL
* Docker
* Nginx proxy
* Swagger UI
* MinIO for file storage
## Features
* User authentication and authorization using JWT tokens
* CRUD operations for fitness recipes
* Asynchronous task execution using JobRunr
* Automatic Excel report generation and storage using MinIO
* API documentation using Swagger UI

## Running this application
To run this application locally, you need to have the following tools installed on your system:

* Docker
* Docker Compose

Follow these steps to run the application:
1. Clone this repository.
2. Navigate to the root directory of the project.
3. Create a new file in the root directory of your project called `.env` and add the environmental variables listed in `.env.template` with their corresponding values
4. Run the following command to build the Docker images and start the containers:
`docker-compose --env-file .env up --build`
5. Wait for the application to start up (this may take a few minutes).
## API Documentation
Upon running the docker-compose, the API documentation is available using Swagger UI. To access it, go to http://localhost:81 in your web browser.

## Contributing
If you find any issues or bugs in the application, please feel free to report them on the Github Issues page for this project. You can also contribute to the project by submitting a pull request with your changes.
