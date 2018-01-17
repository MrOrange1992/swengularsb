package at.fh.ima.swengs.swengular.model;

import info.movito.themoviedbapi.model.*;

import java.util.List;
import java.util.stream.Collectors;


public class Movie
{
    private int id;

    private String title;

    private String director;

    private String posterPath;

    private List<Genre> genres;

    private String homepage;

    private String overview;

    private float userRating;

    private List<Actor> actors;

    private String trailer;


    public Movie() { }

    public Movie(MovieDb movieDb)
    {
        this.id = movieDb.getId();
        this.title = movieDb.getTitle();
        this.posterPath = movieDb.getPosterPath();
        this.genres = movieDb.getGenres();
        this.homepage = movieDb.getHomepage();
        this.overview = movieDb.getOverview();
        this.userRating = movieDb.getUserRating();

        //Mapping for Actors
        if (movieDb.getCast() != null)
        {
            this.actors = movieDb.getCast()
                    .stream()
                    .limit(5)
                    .map(Actor::new)
                    .collect(Collectors.toList());
        }

        //Mapping for Director
        if (movieDb.getCrew() != null)
        {
            movieDb.getCrew().forEach(personCrew ->
            {
                if (personCrew.getJob().equals("Director")) { this.director = personCrew.getName(); }
            });
        }

        //Mapping for Trailer
        // Currently only youtube videos allowed
        if (movieDb.getVideos() != null)
        {
            for (Video video : movieDb.getVideos())
            {
                if (video.getSite().equals("YouTube"))
                {
                    this.setTrailer("https://youtu.be/" + movieDb.getVideos().get(0).getKey());
                    break;
                }
            }
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDirector() { return director; }

    public void setDirector(String director) { this.director = director; }


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }


    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }


    public List<Actor> getActors() { return actors; }

    public void setActors(List<Actor> actors) { this.actors = actors; }


    public String getTrailer() { return trailer; }

    public void setTrailer(String trailer) { this.trailer = trailer; }


    public boolean equals(Object o)
    {
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


    public int hashCode()
    {
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


    public String toString()
    {
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
