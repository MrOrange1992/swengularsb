package at.fh.ima.swengs.swengular.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Movie
{
    @Id
    private long id;

    private String title;

    @ManyToMany(mappedBy = "id")
    private Set<Genre> genres;

    private String posterPath;

    private String homepage;

    private int rating;

    private String cast;

    @Version
    private long version;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

}
