package se.dsve;

import se.dsve.api.ApiService;
import se.dsve.classes.Movie;
import se.dsve.dao.MoviesDAO;
import se.dsve.helpers.InputHelper;

import java.io.IOException;
import java.util.List;

public class Menu {
    private MoviesDAO moviesDAO;
    private ApiService apiService = new ApiService();
    private static InputHelper inputHelper = new InputHelper();
    private static final int EXIT = 5;

    /**
     * Constructs a new Menu object and initializes the application menu.
     *
     * <p>This constructor initializes the Menu object by creating instances of the Database
     * and MoviesDAO classes, and then invokes the {@code showMenu()} method.
     *
     * <p>The Database instance is used to establish a connection to the database, while the
     * MoviesDAO instance handles interactions with the movie database. The showMenu() method
     * presents the main menu options to the user and handles user interaction.
     *
     * @throws IOException if an I/O error occurs while initializing the Menu.
     */
    public Menu() throws IOException {
        // Skapa en Database-instans och skicka den till MoviesDAO
        Database database = new Database();
        moviesDAO = new MoviesDAO(database);

        showMenu(); // Kör showMenu-metoden
    }

    /**
     * Displays the main menu of the application and handles user interaction.
     *
     * <p>This method presents the main menu options to the user and continuously prompts
     * for user input until the user chooses to exit the menu. It reads the user's choice
     * and dispatches the corresponding action based on the selected menu option.
     *
     * <p>The menu options include searching for movies by title, year, actor, or director,
     * as well as the option to exit the application.
     */
    public void showMenu() {
        boolean run = true;
        do {
            int choice = printMenuAndReturnChoice();

            switch (choice) {
                case 1:
                    searchByTitle();
                    break;
                case 2:
                    searchByYear();
                    break;
                case 3:
                    searchByActor();
                    break;
                case 4:
                    searchByDirector();
                    break;
                case EXIT:
                    inputHelper.close();
                    System.out.println("Exiting..");
                    run = false;
                    break;
                default:
                    System.out.println("Invalid choice, try again");
                    break;
            }
        } while (run);
    }

    private static int printMenuAndReturnChoice() {
        String menu = """
                ------------------
                Movie Searcher
                1. Search movie by title
                2. Search movie by year only in database!
                3. Search movie by actor only in database!
                4. Search movie by director only in database!
                5. Exit
                ------------------
                Enter your choice:\s""";

        return inputHelper.promptUserAndGetInt(menu);
    }

    private void searchByTitle() {
        // Hämtar titel på film från användare och initierar movie-objekt
        String s = "Type the name of the movie title: ";
        String title = inputHelper.promptUserAndGetString(s);
        Movie movie;

        // Försöker hämta filmen från databas
        try {
            movie = moviesDAO.findMovieInDatabaseByTitle(title).get(0);
            movie.printMovie();
        }

        // Om filmen inte finns kastar anropet ett undantag och filmen hämtas från OMDB API
        catch (IndexOutOfBoundsException e) {
            movie = getMovieFromApi(title);

            // Om filmen finns i OMDB API läggs den till i databas och skrivs ut till användare
            if (movie != null) {
                moviesDAO.addMovieToDatabase(movie);
                movie.printMovie();
            }
            // Om filmen varken finns i databas eller OMDB API meddelas användaren detta
            else {
                System.out.println("The movie you were looking for could not be found");
            }
        }
    }

    private Movie getMovieFromApi(String title) {
        // Hämtar filmen från API:et
        return apiService.getDataByTitle(title);
    }

    private static boolean notInDatabase(Movie movie) {
        // Initierar MoviesDAO med Databas
        MoviesDAO moviesDAO = new MoviesDAO(new Database());

        // Om filmen INTE finns i databas, returnera true, annars false
        return (moviesDAO.findMovieInDatabaseByTitle(movie.getTitle()) == null);
    }

    private void searchByYear() {
        String s = "Type the year of the movie: ";
        int year = inputHelper.promptUserAndGetInt(s);
        List<Movie> movieListByActor = moviesDAO.findMovieInDatabaseByYear(year);

        if (movieListByActor.isEmpty()) {
            System.out.println("No movies could be found in the database for the specified release year");
        } else {
            for (Movie movie : movieListByActor) {
                movie.printMovie();
            }
        }
    }

    private void searchByActor() {
        String s = "Type the name of an actor: ";
        String actor = inputHelper.promptUserAndGetString(s);
        List<Movie> movieListByActor = moviesDAO.findMovieInDatabaseByActor("%"+actor+"%");

        if (movieListByActor.isEmpty()) {
            System.out.println("No movies starring this actor could be found in the database");
        } else {
            for (Movie movie : movieListByActor) {
                movie.printMovie();
            }
        }

    }

    private void searchByDirector() {
        String s = "Type the name of a director: ";
        String actor = inputHelper.promptUserAndGetString(s);
        List<Movie> movieListByActor = moviesDAO.findMovieInDatabaseByDirector(actor);

        if (movieListByActor.isEmpty()) {
            System.out.println("No movies by this director could be found in the database");
        } else {
            for (Movie movie : movieListByActor) {
                movie.printMovie();
            }
        }
    }
}
