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
    private Set<Genre> genres;

    //@ManyToMany(mappedBy = "id")
    //private Set<MovieList> movieLists;

    private String posterPath;

    private String homepage;

    private String overview;

    private float rating;



    private String cast;

    public Movie(long idMovie, String title)
    {
        this.idMovie = idMovie;
        this.title = title;
    }

    public Movie()
    {

    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public long getId() { return idMovie; }

    public void setId(long idMovie) { this.idMovie = idMovie; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Set<Genre> getGenres() { return genres; }

    public void setGenres(Set<Genre> genres) { this.genres = genres; }

    //TODO FR: handle if genre already exists
    public void addGenre(Genre genre) { this.genres.add(genre); }

    public String getPosterPath() { return posterPath; }

    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getHomepage() { return homepage; }

    public void setHomepage(String homepage) { this.homepage = homepage; }

    public float getRating() { return rating; }

    public void setRating(float rating) { this.rating = rating; }

    public String getCast() { return cast; }

    public void setCast(String cast) { this.cast = cast; }

}
