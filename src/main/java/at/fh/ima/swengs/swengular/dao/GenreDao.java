package at.fh.ima.swengs.swengular.dao;

import at.fh.ima.swengs.swengular.model.Genre;
import at.fh.ima.swengs.swengular.repository.GenreRepository;
import at.fh.ima.swengs.swengular.service.GetProperties;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional
public class GenreDao
{
    private GetProperties gp = new GetProperties();
    private Properties properties = gp.getPropValues();
    private String apiKey = properties.getProperty("apiKey");

    @Autowired
    GenreRepository genreRepository;

    @PersistenceContext
    protected EntityManager entityManager;


    private void persist(Genre genre)
    {
        entityManager.persist(genre);
    }


    public void loadTMDBGenres()
    {
        if (genreRepository.findAll().isEmpty())
        {
            //save all Genres to DB if not already in Db
            TmdbGenre tmdbGenre = new TmdbApi(apiKey).getGenre();
            List<info.movito.themoviedbapi.model.Genre> tmdbGenres = tmdbGenre.getGenreList("en");

            for (info.movito.themoviedbapi.model.Genre tmDBgenre : tmdbGenres)
            {
                if (genreRepository.findByName(tmDBgenre.getName()) == null)
                {
                    Genre genre = new Genre(tmDBgenre.getId(), tmDBgenre.getName());

                    this.persist(genre);
                }
            }
        }
    }



    //TODO: !!NOT WORKING!! not called on init
    /*
    @PostConstruct
    public void init()
    {
        // loadTMDBGenres()
    }
    */
}
