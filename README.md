# Movie Search Console Application

## Overview

This console application interacts with the OMDB movie API, allowing users to search for movies based on various criteria. It takes input from an .env file containing the necessary credentials for accessing the API and the database.
Application Features

* Search for movies by title, year, actor, or director.
* Utilizes the OMDB movie API for fetching movie data.
* Interacts with a MySQL database for storing and retrieving movie information.
* Built-in menu for user interaction and navigation.
* Supports Docker for running the MySQL database.

## Configuration

To use the application, you need to provide the following information in the .env file:

* OMDB_API_KEY={your_api_key}: API key for accessing the OMDB movie API.
* DB_USER={username}: Username for accessing the MySQL database.
* DB_PASSWORD={password}: Password for accessing the MySQL database.
* DB_DRIVER=jdbc:mysql: JDBC driver for connecting to the MySQL database.
* DB_SERVER=localhost: Server address for the MySQL database.
* DB_PORT=3306: Port number for the MySQL database.
* DB_DATABASE=OmdbApiKey: Name of the database containing OMDB API key.

## Application Code

The application is written in Java and organized into several classes:

* Menu.java: Main class for displaying the application menu and handling user interactions.
* Database.java: Class for establishing a connection to the MySQL database.
* AppConfig.java: Configuration class for loading .env variables.
* MoviesDAO.java: Data Access Object (DAO) class for performing CRUD operations with the database.
* ApiService.java: Class for interacting with the OMDB movie API.
* HttpHelper.java: Helper class for handling HTTP connections to the API.
* Movie.java: Class for representing movie objects.
* MovieBuilder.java: Builder class for creating movie objects.

## Running the Application

To run the application, follow these steps:

* Clone the repository to your local machine.
* Set up the .env file with the required credentials.
* Build and run the application using your preferred Java IDE or command-line interface.

The application was designed to run MySQL database with Docker. To use a container to run your MySQL database, follow these steps:

Check that you have Docker installed, otherwise download latest version from (Docker official website)[https://www.docker.com/get-started].

Open terminal and pull MySQL Docker Image:
`docker pull mysql`

After download is complete, run the MySQL container:
`docker run --name my-mysql-container -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=OmdbApiKey -p 3306:3306 -d mysql
`

## Additional Notes

* Ensure that the MySQL database is running, either locally or using Docker.
* Make sure to replace {your_api_key}, {username}, and {password} with your actual credentials in the .env file.
* For more information on how to use the application, refer to the JavaDoc comments in the source code.

Feel free to contribute to the development of this application by submitting pull requests or reporting issues. Happy movie searching!
