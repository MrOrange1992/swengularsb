package at.fh.ima.swengs.swengular.dao;


import at.fh.ima.swengs.swengular.model.Genre;
import at.fh.ima.swengs.swengular.model.Movie;
import at.fh.ima.swengs.swengular.repository.GenreRepository;
import at.fh.ima.swengs.swengular.service.GetProperties;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.Person;
import info.movito.themoviedbapi.model.people.PersonCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Properties;

@Repository
@Transactional
public class MovieDao
{
    private GetProperties gp = new GetProperties();
    private Properties properties = gp.getPropValues();
    private String apiKey = properties.getProperty("apiKey");

    private TmdbMovies tmdbMovies = new TmdbApi(apiKey).getMovies();

    @Autowired
    GenreRepository genreRepository;


    // TODO: not tested!!!
    public Movie mapMovie(int movieID)
    {
        Movie movie = new Movie();

        try
        {
            MovieDb tmdbMovie = tmdbMovies.getMovie(movieID, "en", TmdbMovies.MovieMethod.credits);

            movie.setId(tmdbMovie.getId());
            movie.setTitle(tmdbMovie.getTitle());
            movie.setOverview(tmdbMovie.getOverview());
            movie.setPosterPath(tmdbMovie.getPosterPath());
            movie.setHomepage(tmdbMovie.getHomepage());
            movie.setRating(tmdbMovie.getUserRating());

            //TODO FR: better way to do it or store cast instead of string??
            for (PersonCast castMember : tmdbMovie.getCast())
            {
                movie.setCast(movie.getCast() + "," + castMember.getName());
            }

            // FR: Map themoviedbapi.model.Genre to GenreModel
            for (info.movito.themoviedbapi.model.Genre tmdBGenre : tmdbMovie.getGenres())
            {
                //Genre genre = new Genre(tmdBGenre.getId(), tmdBGenre.getName());

                //TODO FR: genreRepository is null??
                //all genres from TMDB already exist after application start
                Genre genre = genreRepository.findByName(tmdBGenre.getName());
                movie.addGenre(genre);
                genre.addMovie(movie);
            }

        }
        catch (Exception e) { System.out.println(e); }

        return movie;
    }

}
