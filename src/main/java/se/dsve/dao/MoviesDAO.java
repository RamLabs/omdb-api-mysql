package se.dsve.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dsve.Database;
import se.dsve.classes.Movie;
import se.dsve.classes.MovieBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoviesDAO {
    private static final String TABLE_NAME = "movies";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + " (id INT NOT NULL AUTO_INCREMENT, " +
            "title VARCHAR(255), year INT, actors VARCHAR(255), director VARCHAR(255), genre VARCHAR(255), PRIMARY KEY(id))";
    private final Database database;
    private static final Logger logger = LoggerFactory.getLogger(MoviesDAO.class);

    /**
     * Constructs a new MoviesDAO object with the specified database.
     *
     * <p>When instantiated, a MoviesDAO object establishes a connection to the provided database
     * and performs initialization tasks including creating the "movies" table if it does not exist
     * and clearing all existing movie records from the table.
     *
     * <p>The initialization process ensures that the MoviesDAO object is ready to perform operations
     * on the "movies" table in the database.
     *
     * @param database the Database object representing the database connection to be used.
     */
    public MoviesDAO(Database database) {
        this.database = database;
        initializeTable();
        clearMoviesTable();
    }

    public MoviesDAO(Database database, String dbUrl, String user, String password) {
        this.database = database;
        initializeTable(dbUrl, user, password);
        clearMoviesTable(dbUrl, user, password);
    }

    /**
     * Initializes the movies table in the database. If the table does not exist, it creates it.
     *
     * <p>This method connects to the database and create
     * the movies table if it does not already exist. After the operation, the method logs a message
     * indicating the success of the table creation or the existence of the table.
     *
     * <p>If any SQL exception occurs during the initialization, the method logs an error
     * message indicating the failure and prints the SQL exception details using the {@code database.printSQLException(e)} method.
     */
    private void initializeTable() {
        try (Connection connection = database.getConnection();
             Statement prepStat = connection.createStatement()) {
            prepStat.executeUpdate(CREATE_TABLE_SQL);

            // Loggar skapad tabell
            logger.info("Table '{}' created successfully or already exists", TABLE_NAME);
        } catch (SQLException e) {
            // Loggar undantag
            logger.error("Error initializing table '{}': {}", TABLE_NAME, e.getMessage(), e);
            // Skriver ut felmeddelande till användare
            database.printSQLException(e);
        }
    }

    private void initializeTable(String dbUrl, String user, String password) {
        try (Connection connection = database.getConnection(dbUrl, user, password);
             Statement prepStat = connection.createStatement()) {
            prepStat.executeUpdate(CREATE_TABLE_SQL.replace("year","`year`"));

            // Loggar skapad tabell
            logger.info("Table '{}' created successfully or already exists", TABLE_NAME);
        } catch (SQLException e) {
            // Loggar undantag
            logger.error("Error initializing table '{}': {}", TABLE_NAME, e.getMessage(), e);
            // Skriver ut felmeddelande till användare
            database.printSQLException(e);
        }
    }

    // Skapa prepared Statements
    private static final String DELETE_ALL_MOVIES_SQL = "DELETE FROM " + TABLE_NAME;
    private static final String INSERT_MOVIE_SQL = "INSERT INTO " + TABLE_NAME + " (title, year, actors, director, genre) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_FROM = "SELECT * FROM ";
    private static final String SELECT_MOVIE_BY_TITLE_SQL = SELECT_ALL_FROM + TABLE_NAME + " WHERE title = ?";
    private static final String SELECT_MOVIE_BY_ACTOR_SQL = SELECT_ALL_FROM + TABLE_NAME + " WHERE actors LIKE ?";
    private static final String SELECT_MOVIE_BY_YEAR_SQL = SELECT_ALL_FROM + TABLE_NAME + " WHERE year = ?";
    private static final String SELECT_MOVIE_BY_DIRECTOR_SQL = SELECT_ALL_FROM + TABLE_NAME + " WHERE director = ?";

    // Metoder för att hantera databasoperationer

    /**
     * Clears all movie records from the "movies" table in the database.
     *
     * <p>This method establishes a connection to the database and deletes all movie records from the "movies" table.
     * After executing the query, the method logs the number of movies deleted from the table.
     *
     * <p>If any SQL exception occurs during the deletion process, the method logs an error message
     * indicating the failure and prints the SQL exception details using the {@code database.printSQLException(e)}
     * method.
     *
     */
    public void clearMoviesTable() {
        // Försöker skapa anslutning till databas och förbereder statement
        try (Connection connection = database.getConnection();
             Statement prepStat = connection.createStatement()) {

            // Exekverar SQL anrop som tar bort alla filmer i tabellen
            int rowsDeleted = prepStat.executeUpdate(DELETE_ALL_MOVIES_SQL);

            // Loggar antalet filmer som togs bort
            logger.info("Deleted {} movie(s) from the database", rowsDeleted);

        } catch (SQLException e) {
            // Loggar undantag
            logger.error("Error clearing table 'movies': {}", e.getMessage(), e);
            // Skriver ut felmeddelande till användare
            database.printSQLException(e);
        }
    }

    public void clearMoviesTable(String dbUrl, String user, String password) {
        // Försöker skapa anslutning till databas och förbereder statement
        try (Connection connection = database.getConnection(dbUrl, user, password);
             Statement prepStat = connection.createStatement()) {

            // Exekverar SQL anrop som tar bort alla filmer i tabellen
            int rowsDeleted = prepStat.executeUpdate(DELETE_ALL_MOVIES_SQL);

            // Loggar antalet filmer som togs bort
            logger.info("Deleted {} movie(s) from the database", rowsDeleted);

        } catch (SQLException e) {
            // Loggar undantag
            logger.error("Error clearing table 'movies': {}", e.getMessage(), e);
            // Skriver ut felmeddelande till användare
            database.printSQLException(e);
        }
    }

    /**
     * Adds a movie to the "movies" table in the database.
     *
     * <p>This method establishes a connection to the database and inserts the provided movie information into the "movies" table.
     * Movie details such as title, year, actors, director, and genre are set as parameters and stored in the database.
     * After executing the operation, the method logs a message indicating
     * the successful addition of the movie to the database.
     *
     * <p>If any SQL exception occurs during the operation, the method logs an error message
     * indicating the failure and prints the SQL exception details using the {@code database.printSQLException(e)}
     * method.
     *
     * @param movie the Movie object to be added to the database.
     */
    public void addMovieToDatabase(Movie movie) {
        // Försöker skapa anslutning till databas och förbereder statement
        try (Connection connection = database.getConnection();
             PreparedStatement prepStat = connection.prepareStatement(INSERT_MOVIE_SQL)) {

            // Parametrar till Movie-objekt som ska sättas in i tabell
            prepStat.setString(1, movie.getTitle());
            prepStat.setInt(2, movie.getYear());
            prepStat.setString(3, movie.getActors());
            prepStat.setString(4, movie.getDirector());
            prepStat.setString(5, movie.getGenre());

            // Exekverar SQL sträng
            prepStat.execute();

            // Använder logger för att lagra lyckad insättning i tabell
            logger.info("Movie '{}' added to the database", movie.getTitle());
        }
        catch (SQLException e) {
            // Loggar undantag
            logger.error("Error adding movie '{}' to the database: {}", movie.getTitle(), e.getMessage(), e);
            // Skriver ut felmeddelande till användare
            database.printSQLException(e);
        }
    }

    /**
     * Finds movies in the "movies" table in the database by their title.
     *
     * <p>This method establishes a connection to the database and retrieves movies from the "movies" table based on the
     * provided title. If movies with matching titles are found, they are retrieved from the result set and added to
     * a list of Movie objects. The method logs the number of movies found with the provided title.
     *
     * <p>If am SQL exception occurs, the method logs an error message
     * about the failure and prints the SQL exception details using the {@code database.printSQLException(e)}
     * method.
     *
     * @param search the title of the movie to search for.
     * @return a list of Movie objects containing movies with titles matching the search criteria.
     */
    public List<Movie> findMovieInDatabaseByTitle(String search) {
        // Skapar en tom List för att lagra Movie-objekt
        List<Movie> movieList = new ArrayList<>();

        // Skapar anslutning till databas och förbereder statement
        try (Connection connection = database.getConnection();
             PreparedStatement prepStat = connection.prepareStatement(SELECT_MOVIE_BY_TITLE_SQL)) {

            // Färdigställer och exekverar SQL sträng, samt returnerar resultat
            prepStat.setString(1, search);
            ResultSet rs = prepStat.executeQuery();

            // Skapar Movie objekt från ResultSet och om filmer finns lagras de i en List
            movieList = generateListOfMoviesFromResultSet(rs);

                // Loggar antalet filmer som hittades
                logger.info("Found {} movie(s) in the database with title '{}'", movieList.size(), search);
        }
        catch (SQLException e) {
            // Loggar undantag
            logger.error("Error finding movie in the database by title '{}': {}", search, e.getMessage(),e);
            // Skriver ut felmeddelande till användare
            database.printSQLException(e);
        }
        return movieList;
    }

    private List<Movie> generateListOfMoviesFromResultSet(ResultSet rs) throws SQLException {
        // Skapar en lista med Movie objekt
        List<Movie> listOfMovies = new ArrayList<>();

        // Loopar igenom ResultSet och bygger ett Movie objekt
        while (rs.next()) {
            String title = rs.getString(2);
            int year = rs.getInt(3);
            String actors = rs.getString(4);
            String director = rs.getString(5);
            String genre = rs.getString(6);

            // Skapar ett MovieBuilder objekt
            MovieBuilder movieBuilder = new MovieBuilder(title, year, actors, director, genre);

            // Bygger Movie och lägger till List
            listOfMovies.add(movieBuilder.build());
        }

        // Returnerar Lista med Movie objekt
        return listOfMovies;
    }

    /**
     * Finds movies in the "movies" table in the database by actor.
     *
     * <p>This method establishes a connection to the database and retrieves movies from the "movies" table based on the
     * actor. If movies with matching titles are found, they are retrieved from the result set and added to
     * a list of Movie objects. The method logs the number of movies found with the actor.
     *
     * <p>If an SQL exception occurs, the method logs an error message
     * about the failure and prints the SQL exception details using the {@code database.printSQLException(e)}
     * method.
     *
     * @param search the actor to search for in database.
     * @return a list of Movie objects containing movies that the actor was in.
     */
    public List<Movie> findMovieInDatabaseByActor(String search) {
        // Skapar en tom List för att lagra Movie-objekt
        List<Movie> movieList = new ArrayList<>();

        // Skapar anslutning till databas och förbereder statement
        try (Connection connection = database.getConnection();
             PreparedStatement prepStat = connection.prepareStatement(SELECT_MOVIE_BY_ACTOR_SQL)) {

            // Färdigställer och exekverar SQL sträng, samt returnerar resultat
            prepStat.setString(1, search);
            ResultSet rs = prepStat.executeQuery();

            // Skapar Movie objekt från ResultSet och om filmer finns lagras de i en List
            movieList = generateListOfMoviesFromResultSet(rs);

            // Loggar antalet filmer som hittades
            logger.info("Found {} movie(s) in the database with actor '{}'", movieList.size(), search);
        }
        catch (SQLException e) {
            // Loggar undantag
            logger.error("Error finding movie in the database by title '{}': {}", search, e.getMessage(),e);
            // Skriver ut felmeddelande till användare
            database.printSQLException(e);
        }
        return movieList;
    }

    /**
     * Finds movies in the "movies" table in the database by year it was released.
     *
     * <p>This method establishes a connection to the database and retrieves movies from the "movies" table based on the
     * year it was released. If movies with matching titles are found, they are retrieved from the result set and added to
     * a list of Movie objects. The method logs the number of movies released that particular year.
     *
     * <p>If an SQL exception occurs, the method logs an error message
     * about the failure and prints the SQL exception details using the {@code database.printSQLException(e)}
     * method.
     *
     * @param search the year to search for in database.
     * @return a list of Movie objects containing movies that were released that year.
     */
    public List<Movie> findMovieInDatabaseByYear(int search) {
        // Skapar en tom List för att lagra Movie-objekt
        List<Movie> movieList = new ArrayList<>();

        // Skapar anslutning till databas och förbereder statement
        try (Connection connection = database.getConnection();
             PreparedStatement prepStat = connection.prepareStatement(SELECT_MOVIE_BY_YEAR_SQL)) {

            // Färdigställer och exekverar SQL sträng, samt returnerar resultat
            prepStat.setInt(1, search);
            ResultSet rs = prepStat.executeQuery();

            // Skapar Movie objekt från ResultSet och om filmer finns lagras de i en List
            movieList = generateListOfMoviesFromResultSet(rs);

                // Loggar antalet filmer som hittades
                logger.info("Found {} movie(s) in the database with year '{}'", movieList.size(), search);
        }
        catch (SQLException e) {
            // Loggar undantag
            logger.error("Error finding movie in the database by year '{}': {}", search, e.getMessage(),e);
            // Skriver ut felmeddelande till användare
            database.printSQLException(e);
        }
        return movieList;
    }

    /**
     * Finds movies in the "movies" table in the database by director.
     *
     * <p>This method establishes a connection to the database and retrieves movies from the "movies" table based on the
     * director. If movies with matching titles are found, they are retrieved from the result set and added to
     * a list of Movie objects. The method logs the number of movies for that director.
     *
     * <p>If an SQL exception occurs, the method logs an error message
     * about the failure and prints the SQL exception details using the {@code database.printSQLException(e)}
     * method.
     *
     * @param search the director to search for in database.
     * @return a list of Movie objects containing movies by that director.
     */
    public List<Movie> findMovieInDatabaseByDirector(String search) {
        // Skapar en tom List för att lagra Movie-objekt
        List<Movie> movieList = new ArrayList<>();

        // Skapar anslutning till databas och förbereder statement
        try (Connection connection = database.getConnection();
             PreparedStatement prepStat = connection.prepareStatement(SELECT_MOVIE_BY_DIRECTOR_SQL)) {

            // Färdigställer och exekverar SQL sträng, samt returnerar resultat
            prepStat.setString(1, search);
            ResultSet rs = prepStat.executeQuery();

            // Skapar Movie objekt från ResultSet och om filmer finns lagras de i en List
            movieList = generateListOfMoviesFromResultSet(rs);

            // Loggar antalet filmer som hittades
            logger.info("Found {} movie(s) in the database with director '{}'", movieList.size(), search);
        }
        catch (SQLException e) {
            // Loggar undantag
            logger.error("Error finding movie in the database by director '{}': {}", search, e.getMessage(),e);
            // Skriver ut felmeddelande till användare
            database.printSQLException(e);
        }
        return movieList;
    }

    /**
     * Finds movies in the "movies" table in the database.
     *
     * <p>This method establishes a connection to the database and retrieves movies from the "movies" table based on
     * search query. If movies that matches the query are found, they are retrieved from the result set and added to
     * a list of Movie objects. The method logs the number of movies found for that particular query.
     *
     * <p>If an SQL exception occurs, the method logs an error message
     * about the failure and prints the SQL exception details using the {@code database.printSQLException(e)}
     * method.
     *
     * @param sqlQuery the SQL query to be executed.
     * @param search the specific search criteria.
     * @return a list of Movie objects containing movies that meets the search criteria.
     */
    private List<Movie> findInDatabase(String sqlQuery, String search) {
        // Skapar en tom List för att lagra Movie-objekt
        List<Movie> movieList = new ArrayList<>();

        // Skapar anslutning till databas och förbereder statement
        try (Connection connection = database.getConnection();
            PreparedStatement prepStat = connection.prepareStatement(sqlQuery)) {

            // Färdigställer och exekverar SQL sträng, samt returnerar resultat
            prepStat.setString(1, search);
            ResultSet rs = prepStat.executeQuery();

            // Skapar Movie objekt från ResultSet och om filmer finns lagras de i en List
            movieList = generateListOfMoviesFromResultSet(rs);

            // Loggar antalet filmer som hittades
            logger.info(String.format("Found %d movie(s) in the database for '%s'", movieList.size(), search));
        }
        catch (SQLException e) {
            // Loggar undantag
            logger.error(String.format("Error finding movie in the database for %s: %s", search, e.getMessage()),e);
            // Skriver ut felmeddelande till användare
            database.printSQLException(e);
        }
        return movieList;
    }
}
