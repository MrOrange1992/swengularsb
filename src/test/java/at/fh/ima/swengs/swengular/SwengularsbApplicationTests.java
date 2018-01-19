package at.fh.ima.swengs.swengular;


import at.fh.ima.swengs.swengular.model.Movie;
import at.fh.ima.swengs.swengular.model.MovieList;
import at.fh.ima.swengs.swengular.model.User;
import at.fh.ima.swengs.swengular.repository.MovieListRepository;
import at.fh.ima.swengs.swengular.repository.UserRepository;
import at.fh.ima.swengs.swengular.service.TmdbAPI;
import info.movito.themoviedbapi.model.Genre;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwengularsbApplicationTests
{

    @Autowired
    TmdbAPI tmdbAPI;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieListRepository movieListRepository;



    //PASS
    @Test
    public void saveUser()
    {
        //TmdbAPI tmdbAPI = new TmdbAPI();

        User flexBoy = new User("flexboy","flexboy");
        userRepository.save(flexBoy);
    }

    //PASS
    @Test
    public void addMovieListToUser()
    {
        User flexBoy = userRepository.findByUsername("flexboy");

        MovieList list1 = new MovieList("bestOf", flexBoy.getId());

        movieListRepository.save(list1);
    }

    //PASS
    @Test
    public void addMoviesToList()
    {
        User flexBoy = userRepository.findByUsername("flexboy");

        MovieList popularMovieList = movieListRepository.findByName("bestOf");

        tmdbAPI.getPopularMovies(1).forEach(movieDb -> popularMovieList.addMovieID(movieDb.getId()));

        //list1.addMovieID(tmdbAPI.getTmdbMovies().getLatestMovie().getId());

        movieListRepository.save(popularMovieList);
    }

    //PASS
    @Test
    public void getTMDBMoviesFromMovieList()
    {
        MovieList list1 = movieListRepository.findByName("bestOf");

        //OLD with TMDBAPI not implemented in Movielist model
        //list1.getMovieIDs().stream().map(movieId -> tmdbAPI.getMovieByID(movieId)).collect(Collectors.toSet()).forEach(movieDb -> System.out.println(movieDb.getTitle()));
        list1.loadTmdbContent().getMovies().forEach(movieDb -> System.out.println(movieDb.getTitle()));
    }

    //PASS
    @Test
    public void addGenresToUser()
    {
        User flexBoy = userRepository.findByUsername("flexboy");

        Set<Genre> allGenres = tmdbAPI.getAllGenres();

        flexBoy.setGenreIDs(allGenres.stream().map(genre -> genre.getId()).collect(Collectors.toSet()));

        userRepository.save(flexBoy);
    }

    //PASS
    @Test
    public void findAllListsByOwner()
    {
        User flexBoy = userRepository.findByUsername("flexboy");

        Set<MovieList> resultLists = movieListRepository.findAllByOwnerID(flexBoy.getId());

        resultLists.forEach(list -> System.out.println(list.getName()));
    }

    //PASS
    @Test
    public void findListsContainingMovie()
    {
        tmdbAPI.getMovieByID(494089);
        Set<MovieList> resultLists = movieListRepository.findAllByMovieIDsContaining(494089);

        resultLists.forEach(list -> System.out.println(list.getName()));
    }

    //PASS
    @Test
    public void findUsersByGenre()
    {
        Set<User> resultLists = userRepository.findAllByGenreIDsContaining(99);

        resultLists.forEach(user -> System.out.println(user.getUsername()));
    }

    //PASS
    @Test
    public void getPopularMovies()
    {
        MovieList resultList = new MovieList();
        resultList.setName("Popular Movies");

        resultList.setMovies(tmdbAPI.getPopularMovies(1));

        resultList.getMovies().forEach(movie -> System.out.println(movie.getTitle()));
    }

    //PASS
    @Test
    public void getSimilarMovies()
    {
        MovieList resultList = new MovieList();
        resultList.setName("Similar Movies");


        resultList.setMovies(tmdbAPI.getSimilarMovies(354912, 1));

        resultList.getMovies().forEach(movie -> System.out.println(movie.getTitle()));
    }

    //PASS
    @Test
    public void followUser()
    {
        User flexBoy = userRepository.findByUsername("flexboy");

        User luckyLuke = new User("luckyluke","luckyluke");
        userRepository.save(luckyLuke);

        luckyLuke.addUserFollowing(flexBoy);

        userRepository.save(luckyLuke);

        flexBoy.addUserFollowing(luckyLuke);

        userRepository.save(flexBoy);
    }

    //PASS
    @Test
    public void getMoviesOfFollowingUserList()
    {
        User luckyLuke = userRepository.findByUsername("luckyluke");

        Set<MovieList> resultLists = new HashSet<>();

        for (User user : luckyLuke.getUsersFollowing())
        {
            resultLists.addAll(user.getMovieLists());
        }

        resultLists.forEach(movieList -> System.out.println(movieList.getName()));
    }

    //PASS
    @Test
    //Use this function to update movie ID references in DB if they were changed in TMDB
    public void updateTMDBChanges()
    {
        for (MovieList list : movieListRepository.findBy())
        {
            for (Integer movieID : list.getMovieIDs())
            {
                try { tmdbAPI.getMovieByID(movieID); }
                catch (Exception e)
                {
                    list.removeMovieID(movieID);
                    movieListRepository.save(list);
                }
            }
        }

        for (User user : userRepository.findBy())
        {
            for (Integer genreID : user.getGenreIDs())
            {
                try { tmdbAPI.getGenreByID(genreID); }
                catch (Exception e)
                {
                    user.removeGenreID(genreID);
                    userRepository.save(user);
                }
            }
        }
    }

    @Test
    public void getMovieListByName()
    {
        MovieList testlist = movieListRepository.findByName("bestOf");
        System.out.println(testlist.getName());
    }


    @Test
    public void movieCastingCast()
    {
        Movie movie = new Movie(tmdbAPI.getMovieDbByID(8844));
        movie.getActors().forEach(actor-> System.out.println(actor.getName()));
    }

    @Test
    public void searchMoviesByName()
    {
        MovieList movieList = new MovieList();
        movieList.setMovies(tmdbAPI.searchMoviesByName("batman", 1));

        movieList.getMovies().forEach(movie -> System.out.println(movie.getTitle()));
    }

}
