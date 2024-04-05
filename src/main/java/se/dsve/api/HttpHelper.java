package se.dsve.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    // Privat constructor för att förhindra instansiering
    private HttpHelper() {
        throw new AssertionError("Instantiating utility class");
    }

    /**
     * Creates an HTTP connection to the specified URL and retrieves the response as a string using a GET request.
     *
     * @param url The URL to fetch data from.
     * @return A string containing the response from the URL.
     *
     * This method establishes a connection to the given URL, defines it as a GET request,
     * and reads the response from the connection's input stream using a BufferedReader.
     * The method then appends each line of the response to a StringBuilder and returns the entire response as a string.
     *
     * In case of an IOException during the process, the stack trace is printed, and an empty string is returned.
     * It is recommended to handle exceptions appropriately when using this method.
     */
    public static String fetchDataFromUrl(URL url) {
        // Skapar en StringBuilder för att lagra svaret
        StringBuilder response = new StringBuilder();
        try {
           // Öppnar anslutning till URL
           HttpURLConnection connection = (HttpURLConnection) url.openConnection();
           // definierar det som en GET-förfrågan
           connection.setRequestMethod("GET");
           // Skapar en BufferedReader och läser svaret från anslutningens input-ström
           BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

           // Loopar igenom alla rader och lägger till dem i StringBuilder
           String line = "";
           while ((line = reader.readLine()) != null) {
               response.append(line);
           }

           // Stänger BufferedReader
           reader.close();

           // Hanterar situation om ingen data returneras från URL
            if (response.length() == 0) {
                throw new RuntimeException("No data was returned from the specified URL");
            }
       }
       catch (IOException e) {
           e.printStackTrace();
       }
        // Omvandlar StringBuilder till String och returnerar responsen
        return String.valueOf(response);
    }
}