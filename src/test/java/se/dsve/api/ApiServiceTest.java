package se.dsve.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import se.dsve.classes.Movie;

import java.io.*;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiServiceTest {

    private final ApiService apiService = new ApiService();

    private Map<String, String> convertJsonFileToMap(String filePath) throws IOException {
        // Skapar nytt Gson objekt för konvertering och Map som ska returneras
        Gson gson = new Gson();
        Map<String, String> content;

        // Läs in JSON fil till läsare
        try (Reader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, String>>() {}.getType();

            // Konvertering från JSON till Map
            content = gson.fromJson(reader,type);
        }
        return content;
    }

    @DisplayName("Convert valid URL string to URL object")
    @Test
    void convertStringToUrlFormat_ShouldReturnValidURL() throws MalformedURLException {
        // Arrange
        String urlString = "http://example.com";

        // Act
        URL result = apiService.convertStringToUrlFormat(urlString);

        // Assert
        assertEquals(urlString, result.toString(), "URL should match");
    }

    @Test
    @DisplayName("Convert invalid URL string to MalformedURLException")
    void convertStringToUrlFormat_InvalidUrlString() {
        // Arrange
        String invalidUrlString = "invalidUrl";
        // Act
        MalformedURLException thrownException = assertThrows(MalformedURLException.class, () ->
                apiService.convertStringToUrlFormat(invalidUrlString));
        // Assert
        assertEquals("no protocol: invalidUrl", thrownException.getMessage(), "Exception message should match");
    }


    @Test
    @DisplayName("Get movie data by title successfully")
    void getDataByTitle_WhenFound_ThenReturnMovieObject() throws IOException {
        // Arrange
        String filePath = "src/test/resources/apiservicetest_reference_omdb_data.json";
        Map<String, String> result = convertJsonFileToMap(filePath);

        // Act
        Movie movie = apiService.getDataByTitle("Guardians of the Galaxy Vol. 2");

        // Assert
        assertEquals(movie.getTitle(), result.get("Title"));
        assertEquals(String.valueOf(movie.getYear()), result.get("Year"));
        assertEquals(movie.getActors(), result.get("Actors"));
        assertEquals(movie.getDirector(), result.get("Director"));
        assertEquals(movie.getGenre(), result.get("Genre"));
    }
}