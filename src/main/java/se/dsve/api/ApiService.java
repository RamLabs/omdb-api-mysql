package se.dsve.api;

import org.json.JSONObject;
import se.dsve.AppConfig;
import se.dsve.classes.Movie;
import se.dsve.classes.MovieBuilder;

import java.net.MalformedURLException;
import java.net.URL;

public class ApiService {
    private final String API_KEY = AppConfig.getOmdbApiKey();

    /**
     * Fetches movie data from the OMDB API based on the provided movie title.
     *
     * @param movieTitle The title of the movie to retrieve information for.
     * @return A Movie object containing details retrieved from the OMDB API, or null if an error occurs.
     * @throws MalformedURLException If there is an issue with the construction of the URL.
     *
     * This method queries the OMDB API for movie details using the provided title.
     * It fetches data from the OMDB API using the HTTPHelper class.
     * The obtained JSON response is parsed to create a Movie object, and the object is returned.
     */
    public Movie getDataByTitle(String movieTitle) {
        try {
            // Skapar URL-sträng med API-nyckeln och den formaterade titeln
            String formattedTitle = formatToApiStandard(movieTitle);
            URL url = urlToFetch(formattedTitle);

            // Hämtar data från URL:en via HTTP Helper-metod
            String response = HttpHelper.fetchDataFromUrl(url);

            // Konverterar data till JSON objekt
            JSONObject json = convertDataToJSONObject(response);

            // Returnerar Movie med data från OMDB API
            return buildMovieFromJSON(json);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: The provided URL is invalid.");
            System.out.println("Double-check the URL for any typos or missing components.");
        }
        return null;
    }

    private JSONObject convertDataToJSONObject(String data) {
        return new JSONObject(data);
    }

    /**
     * Obtain information about a movie from a JSON object generated using data from OMDB API.
     * A Movie object is then created from title, year, actors, director and genre.
     *
     * @param json the JSONObject file taken as argument
     * @return Movie object with information obtained from OMDB API.
     */
    private Movie buildMovieFromJSON(JSONObject json) {
        // Hämtar data från json-fil erhållen från OMDB API
        String title = json.optString("Title", null);
        int year = json.optInt("Year", -1);
        String actors = json.optString("Actors", null);
        String director = json.optString("Director", null);
        String genre = json.optString("Genre", null);

        // Instansierar ett movieBuilder objekt och bygger ett Movie objekt
        MovieBuilder movieBuilder = new MovieBuilder(title, year, actors, director, genre);
        return movieBuilder.build();
    }

    /**
     * Constructs a URL for fetching movie data from the OMDB API based on the formatted movie title.
     *
     * @param formattedTitle The formatted title of the movie to be included in the API request.
     * @return A URL object representing the complete API request URL.
     * @throws MalformedURLException If there is an issue with the construction of the URL.
     *
     * This method takes the formatted movie title and constructs a URL for making a request
     * to the OMDB API. It concatenates the base OMDB API URL with the API key and the provided formatted title.
     * The resulting URL is returned as a URL object.
     */
    private URL urlToFetch(String formattedTitle) throws MalformedURLException {
        // Sparar URL:en i en variabel omdbApiUrl utan nyckel
        String omdbApiUrl = "http://www.omdbapi.com";

        // Konkatenerar omdbApiUrl med API-nyckeln och den formaterade titeln
        String urlDataRequest = omdbApiUrl + "/?apikey=" + API_KEY + formattedTitle;

        // Skapar en URL-instans med den kompletta strängen
        return new URL(urlDataRequest);
    }

    /**
     * Formats the movie title to comply with OMDB API standards in the API request.
     *
     * @param movieTitle The original title of the movie to be formatted.
     * @return A formatted string containing the movie title according to OMDB API standards.
     *
     * This method takes the original movie title and formats it to comply with the requirements
     * of the OMDB API.
     */
    private static String formatToApiStandard(String movieTitle) {
        // Returnerar formaterad sträng med filmtitel
        return "&t=" + movieTitle;
    }

    /**
     * Converts a string representation of a URL to a URL object.
     *
     * @param urlString A string representation of the URL.
     * @return A URL object created from the provided string.
     * @throws Exception If there is an issue with the conversion or URL instantiation.
     *
     * The purpose of this method is to provide a way to mock URL creation during testing,
     * allowing for easier testing of methods that involve URL instantiation.
     * It takes a string representation of a URL and returns a corresponding URL object.
     */
    protected URL convertStringToUrlFormat(String urlString) throws MalformedURLException {
        // Skapar och returnerar ett URL objekt utifrån urlString
        return new URL(urlString);
    }

}
