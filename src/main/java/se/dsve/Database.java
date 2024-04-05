package se.dsve;

import java.sql.*;

public class Database {
    // Skapa URL till databasen
    private static final String DB_DATABASE = AppConfig.getDbDatabase();
    private static final String DB_DRIVER = AppConfig.getDbDriver();
    private static final String DB_SERVER = AppConfig.getDbServer();
    private static final String DB_PORT = AppConfig.getDbPort();
    private static final String DB_URL = DB_DRIVER + "://" + DB_SERVER + ":" + DB_PORT + "/";
    private static final String JDBC_URL = DB_URL + DB_DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true";

    private final String jdbcUsername = AppConfig.getDbUser();
    private final String jdbcPassword = AppConfig.getDbPassword();

    // SQL query for creating the database
    private static final String CREATE_DATABASE_SQL = "CREATE DATABASE IF NOT EXISTS " + AppConfig.getDbDatabase();

    /**
     * Constructs a new Database object and initializes the database schema.
     *
     * <p>This constructor initializes the Database object by calling the {@code initializeDatabase()} method,
     * which creates the necessary database schema. It ensures that the database is properly configured and
     * ready for use.
     */
    public Database() {
        initializeDatabase();
    }

    /**
     * Constructs a new Database object for testing purposes and initializes the test database schema.
     *
     * <p>This constructor initializes the Database object for testing purposes by calling the
     * {@code initializeDatabase()} method with the provided parameters, which creates the necessary
     * test database schema. It ensures that the test database is properly configured and ready for
     * use upon instantiation of the Database object.
     *
     * @param DB_URL the URL of the test database to connect to.
     * @param DB_DATABASE the name of the test database schema to create.
     * @param jdbcUsername the username used for authentication.
     * @param jdbcPassword the password used for authentication.
     */
    public Database(String DB_URL, String DB_DATABASE, String jdbcUsername, String jdbcPassword) {
        initializeDatabase(DB_URL, DB_DATABASE, jdbcUsername, jdbcPassword);
    }

    /**
     * Initializes the database by creating the necessary database schema.
     *
     * <p>This method creates the database schema by executing an SQL statement to
     * create the database tables and other structures required for operation.
     *
     * <p>The initialization process is performed by establishing a connection to
     * the database and executing the SQL statement to create the schema. Any SQL
     * exceptions that occur during this process are printed to the standard error stream.
     *
     * <p>This method is typically called during the setup phase to ensure that the
     * database is properly configured and ready for use.
     */
    private void initializeDatabase() {
        // Försöker skapa anslutning till databas och skapar statement
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            // Utför operation genom att skapa databas om den inte existerar
            statement.executeUpdate(CREATE_DATABASE_SQL);
        } catch (SQLException e){
            // Skriver ut felmeddelande till användare
            printSQLException(e);
        }
    }

    /**
     * Initializes the test database by creating the necessary database schema.
     *
     * <p>This method creates the database schema for testing purposes by executing an SQL statement
     * to create the database schema if it does not already exist.
     *
     * <p>The initialization process is performed by establishing a connection to the test database
     * using the provided database URL, JDBC username, and password, and executing the SQL statement
     * to create the schema. Any SQL exceptions that occur during this process are printed to the
     * standard error stream.
     *
     * <p>This method is intended for testing purposes only to ensure that the test database is properly
     * configured and ready for use.
     *
     * @param DB_URL the URL of the test database to connect to.
     * @param DB_DATABASE the name of the test database schema to create.
     * @param jdbcUsername the username used for authentication.
     * @param jdbcPassword the password used for authentication.
     */
    private void initializeDatabase(String DB_URL, String DB_DATABASE, String jdbcUsername, String jdbcPassword) {
        try (Connection connection = getConnection(DB_URL, jdbcUsername, jdbcPassword);
             Statement statement = connection.createStatement()) {
             statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + DB_DATABASE);
        } catch (SQLException e){
            printSQLException(e);
        }
    }

    /**
     * Prints information about the specified SQLException to the standard error stream.
     *
     * <p>This method prints details about the provided SQLException, including the SQL state,
     * error code, and error message, to the standard error stream. Additionally, it traverses
     * through the exception chain to print information about any nested causes.
     *
     * @param ex the SQLException for which details are to be printed.
     */
    public void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    /**
     * Establishes a connection to the database using the JDBC driver.
     *
     * <p>This method initializes a connection to the database using the JDBC driver,
     * specified by the database URL, JDBC username, and password provided during
     * class instantiation. It returns the established Connection object.
     *
     * <p>If an SQL exception occurs during the connection,
     * details about the exception are printed to the standard error stream.
     *
     * @return a Connection object representing the established database connection,
     *         or null if the connection could not be established.
     */
    public Connection getConnection() {
        // Deklarerar objekt för anslutning
        Connection connection = null;

        // Försöker skapa anslutning till databas
        try {
            connection = DriverManager.getConnection(JDBC_URL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // Skriver ut felmeddelande till användare
            printSQLException(e);
        }
        return connection;
    }

    /**
     * Establishes a connection to the database using the provided database URL, username, and password.
     *
     * <p>This method is intended for testing purposes only. It establishes a connection to the database
     * using the specified database URL, JDBC username, and password. It returns the established Connection
     * object.
     *
     * <p>If an SQL exception occurs during the connection establishment process, details about the exception
     * are printed to the standard error stream.
     *
     * @param DB_URL the URL of the database to connect to.
     * @param jdbcUsername the username used for authentication.
     * @param jdbcPassword the password used for authentication.
     * @return a Connection object representing the established database connection,
     *         or null if the connection could not be established.
     */
    public Connection getConnection(String DB_URL, String jdbcUsername, String jdbcPassword) {
        // Deklarerar objekt för anslutning
        Connection connection = null;

        // Försöker skapa anslutning till databas
        try {
            connection = DriverManager.getConnection(DB_URL , jdbcUsername, jdbcPassword);

        } catch (SQLException e) {
            // Skriver ut felmeddelande till användare
            printSQLException(e);
        }
        return connection;
    }
}
