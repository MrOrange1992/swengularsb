package at.fh.ima.swengs.swengular.service;

import at.fh.ima.swengs.swengular.model.Genre;
import at.fh.ima.swengs.swengular.model.Movie;
import at.fh.ima.swengs.swengular.model.MovieList;
import at.fh.ima.swengs.swengular.repository.GenreRepository;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbGenre;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TmdbAPI
{
    //ATTRIBUTES
    //------------------------------------------------------------------------------------------------------------------
    private GetProperties gp = new GetProperties();
    private Properties properties = gp.getPropValues();
    private String apiKey = properties.getProperty("apiKey");
    private TmdbApi tmdbApi =  new TmdbApi(apiKey);
    private TmdbMovies tmdbMovies = tmdbApi.getMovies();

    @Autowired
    GenreRepository genreRepository;


    //------------------------------------------------------------------------------------------------------------------



    //CONSTRUCTOR
    //------------------------------------------------------------------------------------------------------------------
    public TmdbAPI() {}
    //------------------------------------------------------------------------------------------------------------------



    //GETTER/SETTER
    //------------------------------------------------------------------------------------------------------------------
    public TmdbMovies getTmdbMovies() { return tmdbMovies; }
    //------------------------------------------------------------------------------------------------------------------



    //METHODS
    //------------------------------------------------------------------------------------------------------------------
    /***
     *
     * cause genre wants movie object from db that isnt yet there
     * @param tmdbMovie
     * @return
     */
    @Transactional
    public Movie mapMovie(MovieDb tmdbMovie)
    {
        Movie movie = new Movie();

        //try
        //{
            movie.setId(tmdbMovie.getId());
            movie.setTitle(tmdbMovie.getTitle());
            movie.setOverview(tmdbMovie.getOverview());
            movie.setPosterPath(tmdbMovie.getPosterPath());
            movie.setHomepage(tmdbMovie.getHomepage());
            movie.setRating(tmdbMovie.getUserRating());

            //can be null
            if (tmdbMovie.getCast() != null)
            {
                for (PersonCast castMember : tmdbMovie.getCast())
                {
                    //TODO All: better way to do it or store cast instead of string??
                    movie.setCast(movie.getCast() + "," + castMember.getName());
                }
            }
            //tmdbMovie.getCast().forEach(castMember -> movie.setCast(movie.getCast() + "," + castMember));


            //can be null
            if (tmdbMovie.getGenres() != null)
            {
                for (info.movito.themoviedbapi.model.Genre tmdBGenre : tmdbMovie.getGenres())
                {
                    //all genres from TMDB already exist after application start
                    Genre genre = genreRepository.findByName(tmdBGenre.getName());
                    movie.addGenre(genre);
                    genre.addMovie(movie);
                    //TODO FR: persistence.EntityNotFoundException: cause genre wants movie object from db that isnt yet there
                    genreRepository.save(genre);
                }
            }
        //}
        //catch (Exception e) { System.out.println(e); }

        return movie;
    }



    /***
     * TODO FR: map function for collection of movies returned as MovieList
     * @return
     */
    public MovieList mapMovies()
    {
        return null;
    }



    /***
     * Map all "en" Genres from TMDB to our Genre model
     * This function should be called once @ setup of application
     *
     * @return  Collection of all available Genres
     */
    public Set<Genre> mapAllTmdbGenres()
    {
        TmdbGenre tmdbGenre = tmdbApi.getGenre();

        List<info.movito.themoviedbapi.model.Genre> tmdbGenres = tmdbGenre.getGenreList("en");

        //using lambda expression for mapping all genres
        return tmdbGenres.stream().map(genre -> new Genre(genre.getId(), genre.getName())).collect(Collectors.toSet());
    }
    //------------------------------------------------------------------------------------------------------------------




}
