package at.fh.ima.swengs.swengular.controller;

import at.fh.ima.swengs.swengular.repository.GenreRepository;
import at.fh.ima.swengs.swengular.service.GetProperties;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbGenre;
import info.movito.themoviedbapi.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

public class GenreController
{

    private GetProperties gp = new GetProperties();
    private Properties properties = gp.getPropValues();
    private String apiKey = properties.getProperty("apiKey");


    @Autowired
    GenreRepository genreRepository;


    //TODO: not called on init ??????
    @PostConstruct
    public void init()
    {
        if (genreRepository.findAll().isEmpty())
        {
            //save all Genres to DB if not already in Db
            TmdbGenre tmdbGenre = new TmdbApi(apiKey).getGenre();
            List<Genre> tmdbGenres = tmdbGenre.getGenreList("en");

            //

            for (info.movito.themoviedbapi.model.Genre tmDBgenre : tmdbGenres)
            {
                if (genreRepository.findByName(tmDBgenre.getName()) == null)
                {
                    at.fh.ima.swengs.swengular.model.Genre genre = new at.fh.ima.swengs.swengular.model.Genre(tmDBgenre.getId(), tmDBgenre.getName());
                    genreRepository.persist(genre);
                }
            }
        }

    }
}
