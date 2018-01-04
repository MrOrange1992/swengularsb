package at.fh.ima.swengs.swengular.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.tools.javah.Gen;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.MovieList;
import info.movito.themoviedbapi.model.core.MovieKeywords;
import info.movito.themoviedbapi.model.core.ResultsPage;
import info.movito.themoviedbapi.model.people.PersonCast;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//DEPRECATED
//Will use TMDBmovie instead (MovieDb)




public class Movie extends MovieDb
{

    private int id;

    private String title;

    private String posterPath;

    private List<Genre> genres;

    private String homepage;

    private String overview;

    private float userRating;

    //TODO: Cast


    public Movie(){

    }

    public Movie(MovieDb mov) {
        this.id = mov.getId();
        this.title = mov.getTitle();
        this.posterPath = mov.getPosterPath();
        this.genres = mov.getGenres();
        this.homepage = mov.getHomepage();
        this.overview = mov.getOverview();
        this.userRating = mov.getUserRating();
    }


    public Movie(int id, String title, String posterPath, List<Genre> genres, String homepage, String overview, float userRating) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.genres = genres;
        this.homepage = homepage;
        this.overview = overview;
        this.userRating = userRating;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (Float.compare(movie.userRating, userRating) != 0) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (posterPath != null ? !posterPath.equals(movie.posterPath) : movie.posterPath != null) return false;
        if (genres != null ? !genres.equals(movie.genres) : movie.genres != null) return false;
        if (homepage != null ? !homepage.equals(movie.homepage) : movie.homepage != null) return false;
        return overview != null ? overview.equals(movie.overview) : movie.overview == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + (genres != null ? genres.hashCode() : 0);
        result = 31 * result + (homepage != null ? homepage.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (userRating != +0.0f ? Float.floatToIntBits(userRating) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", genres=" + genres +
                ", homepage='" + homepage + '\'' +
                ", overview='" + overview + '\'' +
                ", userRating=" + userRating +
                '}';
    }
}
