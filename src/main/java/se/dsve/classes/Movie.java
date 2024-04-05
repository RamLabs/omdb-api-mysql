package se.dsve.classes;

public class Movie {
    // Antag att dessa är dina instansvariabler
    private String title;
    private int year;
    private String actors;
    private String director;
    private String genre;

    // Antag att detta är din konstruktor
    /**
     * Constructs a new Movie object with the specified title, year, actors, director, and genre.
     *
     * <p>The constructor initializes a Movie object with the provided attributes: title, year,
     * actors, director, and genre.
     *
     * @param title the title of the movie.
     * @param year the release year of the movie.
     * @param actors the actors starring in the movie.
     * @param director the director of the movie.
     * @param genre the genre of the movie.
     */
    public Movie(String title, int year, String actors, String director, String genre) {
        this.title = title;
        this.year = year;
        this.actors = actors;
        this.director = director;
        this.genre = genre;
    }

    // Metod för att skriva ut filmens detaljer

    /**
     * Prints the information of the movie such as title, year, genre, director and actors.
     *
     * <p>The method retrieves information about the Movie object attributes movie title, release year, genre, director
     * and starring actors. </p>
     */
    public void printMovie() {
        System.out.println("Title: " + getTitle());
        System.out.println("Year: " + getYear());
        System.out.println("Genre: " + getGenre());
        System.out.println("Director: " + getDirector());
        System.out.println("Actors: " + getActors());
    }

    //getters and setters for the movie object
    /**
     * Returns the title of the movie.
     *
     * @return the title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title the title of the movie to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the release year of the movie.
     *
     * @return the release year of the movie.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the release year of the movie.
     *
     * @param year the release year of the movie to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the actors starring in the movie.
     *
     * @return the actors starring in the movie.
     */
    public String getActors() {
        return actors;
    }

    /**
     * Sets the actors starring in the movie.
     *
     * @param actors the actors starring in the movie to set.
     */
    public void setActors(String actors) {
        this.actors = actors;
    }

    /**
     * Returns the director of the movie.
     *
     * @return the director of the movie.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Sets the director of the movie.
     *
     * @param director the director of the movie to set.
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Returns the genre of the movie.
     *
     * @return the genre of the movie.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the movie.
     *
     * @param genre the genre of the movie to set.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Returns a string representation of the Movie object.
     *
     * @return a string representation of the Movie object.
     */
    public String toString() {
        return "Movie{" + "title='" + title + '\'' + ", year='" + year + '\'' + ", actors='" + actors + '\'' + ", director='" + director + '\'' + ", Genre='" + genre + '\'' + '}';
    }
}


