package se.dsve.classes;

public class MovieBuilder {

    private String title;
    private int year;
    private String actors;
    private String director;
    private String genre;

    /**
     * Constructs a new MovieBuilder object with the specified title, year, actors, director, and genre.
     * The class is a builder class for Movie.
     *
     * <p>The constructor initializes a MovieBuilder object with the provided attributes: title, year,
     * actors, director, and genre.
     *
     * @param title the title of the movie.
     * @param year the release year of the movie.
     * @param actors the actors starring in the movie.
     * @param director the director of the movie.
     * @param genre the genre of the movie.
     */
    public MovieBuilder(String title, int year, String actors, String director, String genre) {
        this.title = title;
        this.year = year;
        this.actors = actors;
        this.director = director;
        this.genre = genre;
    }

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
     * @param title the title of the movie to be set.
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
     * Sets the year of the movie.
     *
     * @param year the year of the movie to be set.
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
     * Constructs a new Movie object based on the properties set in the MovieBuilder.
     *
     * <p>This method creates a new Movie object using the properties (title, year, actors, director, and genre)
     * that have been previously set in the MovieBuilder instance. It then returns the newly constructed Movie object.
     *
     * <p>The Movie object is constructed with the specified properties, allowing for flexible and customizable
     * creation of Movie instances.
     *
     * @return a new Movie object with the properties set in the MovieBuilder.
     */
    public Movie build() {
        return new Movie(title, year, actors, director, genre);
    }
}
