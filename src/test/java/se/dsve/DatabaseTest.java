package se.dsve;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class DatabaseTest {
    private static final String DB_NAME = AppConfig.getDbDatabase();
    private static final String JDBC_URL = "jdbc:h2:mem:test;";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static Database database;

    @BeforeAll
    static void setUp() throws SQLException {
        // Öppna en anslutning till H2 in-memory databas
        database = new Database(JDBC_URL + "DB_CLOSE_DELAY=-1", DB_NAME, USER, PASSWORD);
    }

    private boolean databaseExists(Connection connection, String dbName) throws SQLException {
        // Check if the specified database exists in the H2 in-memory database
        ResultSet rs = connection.getMetaData().getSchemas();

        // Lagrar Catalogs ur ResultSet i List
        List<String> catalogNames = new ArrayList<>();

        while (rs.next()) {
            String existingSchema = rs.getString("TABLE_SCHEM");
            if (dbName.equalsIgnoreCase(existingSchema)) {
                return true;
            }
        }
        return false;
    }

    @Test
    void testinitializeDatabase() throws SQLException {
        // Assert
        assertTrue(databaseExists(database.getConnection(JDBC_URL, USER, PASSWORD), DB_NAME), "Database should exist");
    }

    @Test
    void testGetConnection_Success() throws SQLException {
        // Act
        Connection testConnection = database.getConnection(JDBC_URL, USER, PASSWORD);

        // Assert
        assertNotNull(testConnection, "Connection should not be null");
        assertTrue(testConnection.isValid(5), "Connection should be valid");
    }
}