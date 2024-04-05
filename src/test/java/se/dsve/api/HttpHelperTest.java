package se.dsve.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HttpHelperTest {

    /**
     * Reads the content of a file and returns it as a string.
     *
     * @param file The {@code File} object representing the file to be read.
     * @return A {@code String} containing the content of the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    private String readFromFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    @Test
    void fetchDataFromUrl_Success() throws IOException {
        // Använder "golden-file testing" där JSON fil jämförs med resultat från fetchDataFromUrl metod
        // Ange en känd URL till OMDB API
        URL knownUrl = new URL("http://www.omdbapi.com/?i=tt3896198&apikey=5da2dbd9");

        // Hämtar förväntad respons från JSON-fil
        File expectedResponseFile = new File("src/test/resources/httphelpertest_expected_response.json");
        String expectedResponse = readFromFile(expectedResponseFile);

        // Call the method to test
        String actualResponse = HttpHelper.fetchDataFromUrl(knownUrl);

        // Antar att båda är lika
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void fetchDataFromUrl_NullURL() {
        // Mockar URL klass
        URL mockUrl = mock(URL.class);

        // Antar att metoden kastar ett RuntimeException för den mockade URL:n
        assertThrows(RuntimeException.class, () -> HttpHelper.fetchDataFromUrl(mockUrl));
    }
}