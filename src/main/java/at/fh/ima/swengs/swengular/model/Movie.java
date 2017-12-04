package at.fh.ima.swengs.swengular.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Movie
{
    @Version
    private long version;

    @Id
    private long idMovie;

    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    //@JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "idMovie", referencedColumnName = "idMovie"), inverseJoinColumns = @JoinColumn(name = "idGenre", referencedColumnName = "idGenre"))
    private List<Genre> genres;

    //@ManyToMany(mappedBy = "id")
    //private Set<MovieList> movieLists;

    private String posterPath;

    private String homepage;

    private int rating;

    private String cast;

    public Movie(long idMovie, String title,
                 List<Genre> genres,
                 String posterPath,
                 String homepage,
                 int rating,
                 String cast) {
        this.idMovie = idMovie;
        this.title = title;
        this.genres = genres;
        this.posterPath = posterPath;
        this.homepage = homepage;
        this.rating = rating;
        this.cast = cast;
    }

    public long getId() { return idMovie; }

    public void setId(long idMovie) { this.idMovie = idMovie; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public List<Genre> getGenres() { return genres; }

    public void setGenres(List<Genre> genres) { this.genres = genres; }

    public String getPosterPath() { return posterPath; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getHomepage() { return homepage; }

    public void setHomepage(String homepage) { this.homepage = homepage; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public String getCast() { return cast; }

    public void setCast(String cast) { this.cast = cast; }

}
