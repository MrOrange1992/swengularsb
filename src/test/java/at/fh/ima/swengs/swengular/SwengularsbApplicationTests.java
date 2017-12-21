package at.fh.ima.swengs.swengular;

import at.fh.ima.swengs.swengular.model.Genre;
import at.fh.ima.swengs.swengular.repository.GenreRepository;
import at.fh.ima.swengs.swengular.repository.MovieRepository;
import at.fh.ima.swengs.swengular.service.TmdbAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwengularsbApplicationTests
{
    @Autowired
    GenreRepository genreRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TmdbAPI tmdbAPI;// = new TmdbAPI();


    //PASS
	//@Test
	public void saveGenre()
	{
        Genre g1 = new Genre(1, "Action");
        genreRepository.save(g1);
    }

    //PASS
    //@Test
    public void saveAllGenres()
    {
        //Repository is able to save collection
        genreRepository.save(tmdbAPI.mapAllTmdbGenres());
    }

    //FAIL
    @Test
    public void saveMovie()
    {
        //TmdbAPI tmdbAPI = new TmdbAPI();

        movieRepository.save(tmdbAPI.mapMovie(tmdbAPI.getTmdbMovies().getMovie(493960, "en")));
    }





}
