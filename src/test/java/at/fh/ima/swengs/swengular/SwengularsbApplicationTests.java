package at.fh.ima.swengs.swengular;


import at.fh.ima.swengs.swengular.model.MovieList;
import at.fh.ima.swengs.swengular.model.User;
import at.fh.ima.swengs.swengular.repository.MovieListRepository;
import at.fh.ima.swengs.swengular.repository.UserRepository;
import at.fh.ima.swengs.swengular.service.TmdbAPI;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
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

        User flexBoy = new User("Flex", "Boy", "flexboy");
        userRepository.save(flexBoy);
    }

    //PASS
    @Test
    public void findUser()
    {
        User flexBoy = userRepository.findByFirstName("Flex");

        System.out.println(flexBoy.getFirstName());
    }

    //PASS
    @Test
    public void addMovieListToUser()
    {
        User flexBoy = userRepository.findByFirstName("Flex");

        MovieList list1 = new MovieList("bestOf", flexBoy);

        movieListRepository.save(list1);
    }

    //PASS
    @Test
    public void getTMDBMoviesFromMovieList()
    {
        MovieList list1 = movieListRepository.findByName("bestOf");


        list1.getMovieIDs().stream().map(movieId -> tmdbAPI.getMovieByID(movieId)).collect(Collectors.toSet()).forEach(movieDb -> System.out.println(movieDb.getTitle()));
    }

    //PASS
    @Test
    public void addMoviesToList()
    {
        User flexBoy = userRepository.findByFirstName("Flex");

        MovieList popularMovieList = movieListRepository.findByName("bestOf");

        tmdbAPI.getPopularMovies(1).forEach(movieDb -> popularMovieList.addMovieID(movieDb.getId()));

        //list1.addMovieID(tmdbAPI.getTmdbMovies().getLatestMovie().getId());

        movieListRepository.save(popularMovieList);
    }

    //PASS
    @Test
    public void addGenresToUser()
    {
        User flexBoy = userRepository.findByFirstName("Flex");

        Set<Genre> allGenres = tmdbAPI.getAllGenres();

        flexBoy.setGenreIDs(allGenres.stream().map(genre -> genre.getId()).collect(Collectors.toSet()));

        userRepository.save(flexBoy);
    }

    //PASS
    @Test
    public void findAllListsByOwner()
    {
        User flexBoy = userRepository.findByFirstName("Flex");

        Set<MovieList> resultLists = movieListRepository.findAllByOwner(flexBoy);

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

        resultLists.forEach(user -> System.out.println(user.getFirstName()));
    }

    //PASS
    @Test
    public void getPopularMovies()
    {
        Set<MovieDb> resultList = tmdbAPI.getPopularMovies(1);

        resultList.forEach(movie -> System.out.println(movie.getTitle()));
    }

    //PASS
    @Test
    public void getSimilarMovies()
    {
        Set<MovieDb> resultList = tmdbAPI.getSimilarMovies(354912, 1);

        resultList.forEach(movie -> System.out.println(movie.getTitle()));
    }

    //PASS
    @Test
    public void followUser()
    {
        User flexBoy = userRepository.findByFirstName("Flex");

        User luckyLuke = new User("Lucky", "Luke", "luckyluke");
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
        User luckyLuke = userRepository.findByFirstName("Lucky");

        tmdbAPI.getTmdbCollectionOfAllUserLists(luckyLuke.getUsersFollowing());
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


}
