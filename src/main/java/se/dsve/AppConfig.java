package se.dsve;

import io.github.cdimascio.dotenv.Dotenv;

public class AppConfig {

    static Dotenv dotenv = Dotenv.load();
    private static final String OMDB_API_KEY = dotenv.get("OMDB_API_KEY");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
    private static final String DB_DRIVER = dotenv.get("DB_DRIVER");
    private static final String DB_SERVER = dotenv.get("DB_SERVER");
    private static final String DB_PORT = dotenv.get("DB_PORT");
    private static final String DB_DATABASE = dotenv.get("DB_DATABASE");

    public static String getOmdbApiKey() {
        return OMDB_API_KEY;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPassword() {
        return DB_PASSWORD;
    }

    public static String getDbDriver() {
        return DB_DRIVER;
    }

    public static String getDbServer() {
        return DB_SERVER;
    }

    public static String getDbPort() {
        return DB_PORT;
    }

    public static String getDbDatabase() {
        return DB_DATABASE;
    }
}
