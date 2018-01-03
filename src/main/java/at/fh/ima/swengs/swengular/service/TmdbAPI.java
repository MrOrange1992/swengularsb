package at.fh.ima.swengs.swengular.service;

//DEPRECATED
//import at.fh.ima.swengs.swengular.model.Genre;
//import at.fh.ima.swengs.swengular.model.Movie;
import at.fh.ima.swengs.swengular.model.MovieList;
import at.fh.ima.swengs.swengular.model.User;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Genre;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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

    private Map<Integer, Genre> genreIDMap = tmdbApi.getGenre().getGenreList("en").stream()
            .collect(Collectors.toMap(Genre::getId, Function.identity()));

    private Map<String, Genre> genreNameMap = tmdbApi.getGenre().getGenreList("en").stream()
            .collect(Collectors.toMap(Genre::getName, Function.identity()));

    //DEPRECATED
    //@Autowired
    //GenreRepository genreRepository;
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
    /*** DEPRECATED
     *
     * cause genre wants movie object from db that isnt yet there
     * @param tmdbMovie
     * @return
     */
    /*
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
                for (Genre tmdBGenre : tmdbMovie.getGenres())
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

    /**
     * Map all "en" Genres from TMDB to our Genre model
     * This function should be called once @ setup of application
     *
     * @return  Collection of all available Genres
     *
    public Set<Genre> mapAllTmdbGenres()
    {
        TmdbGenre tmdbGenre = tmdbApi.getGenre();

        List<Genre> tmdbGenres = tmdbGenre.getGenreList("en");

        //using lambda expression for mapping all genres
        //return tmdbGenres.stream().map(genre -> new Genre(genre.getId(), genre.getName())).collect(Collectors.toSet());
        return null;
    }
    */

    /*** DEPRECATED
     * TODO FR: map function for collection of movies returned as MovieList
     * @return
     */
    public MovieList mapMovies()
    {
        return null;
    }


    /**
     * Get TMDBmovie by ID
     * @param movieID id of TMDBmovie
     * @return  TMDBmovie
     */
    public MovieDb getMovieByID(int movieID)
    {
        return tmdbMovies.getMovie(movieID, "en");

        /*TODO: ResourceNotFound
            handle ResourceNotFound returned by TMDB if MovieDb ID has been updated in TMDB
            maybe use update - function (updateTMDBChanges()) from testcases
            or build custom Exception to Invoke UpdateFunction internally on error
        */

        /* 26.12.2017 -> resource not found exception returned by TMDB for about 10min
        try
        {
            return tmdbMovies.getMovie(movieID, "en");
        }
        catch (Exception e)
        {
            return null;
        }
        */
    }

    /** DEPRECATED -> handled by MovieList.loadTmdbContent()
     * Get set of TMDB movies of specified MovieList
     * @param movieList List to get TMDBmovies from
     * @return Set of TMDBmovies

    public Set<MovieDb> getMoviesOfList(MovieList movieList)
    {
        return movieList.getMovieIDs().stream().map(movieID -> getMovieByID(movieID)).collect(Collectors.toSet());
    }
    */

    /**
     * Get set of TMDB movies of multiple IDs
     * @param IDs set of IDs to get Tmdb content from
     * @return Set of TMDBmovies
     */
    public Set<MovieDb> getMoviesOfIDs(Set<Integer> IDs)
    {
        return IDs.stream().map(movieID -> getMovieByID(movieID)).collect(Collectors.toSet());
    }

    /**
     * Get TMDBGenre by ID
     * @param genreID ID of TMDBgenre
     * @return TMDBgenre
     */
    public Genre getGenreByID(int genreID) { return genreIDMap.get(genreID); }

    /**
     * Get TMDBGenre by name
     * @param genreName name of TMDBgenre
     * @return TMDBgenre
     */
    public Genre getGenreByName(String genreName) { return genreNameMap.get(genreName); }

    /**
     * Get all TMDBgenres (en)
     * @return Set of TMDBgenres
     */
    public Set<Genre> getAllGenres()
    {
        return new HashSet<Genre>(genreIDMap.values());
    }

    /**
     * Get collection of popular TMDBmovies
     * @param resultPages number of resultpages to return
     * @return set of TMDBmovies
     */
    public Set<MovieDb> getPopularMovies(int resultPages)
    {
        return new HashSet<MovieDb>(tmdbMovies.getPopularMovies("en", resultPages).getResults());
    }

    /**
     * Get collection of similar TMDBmovies
     * @param movieID movie to get similar results from
     * @param resultPages number of resultpages to return
     * @return  set of TMDBmovies
     */
    public Set<MovieDb> getSimilarMovies(int movieID, int resultPages)
    {
        return new HashSet<MovieDb>(tmdbMovies.getSimilarMovies(movieID, "en", resultPages).getResults());
    }
    //------------------------------------------------------------------------------------------------------------------




}
