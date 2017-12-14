package at.fh.ima.swengs.swengular;

import at.fh.ima.swengs.swengular.repository.GenreRepository;
import at.fh.ima.swengs.swengular.service.GetProperties;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Properties;

@SpringBootApplication
public class SwengularsbApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SwengularsbApplication.class, args);


		// TESTS ---------------------------------------------------------------------
		GetProperties gp = new GetProperties();
		Properties properties = gp.getPropValues();

		String apiKey = properties.getProperty("apiKey");

		//TmdbSearch tmdbSearch = new TmdbApi(apiKey).getSearch();

		TmdbMovies movies = new TmdbApi(apiKey).getMovies();
		MovieDb movie = movies.getMovie(5353, "en");

		/*
		if (this.genreRepository.findAll().isEmpty())
		{
			//save all Genres to DB if not already in Db
			TmdbGenre tmdbGenre = new TmdbApi(apiKey).getGenre();
			List<info.movito.themoviedbapi.model.Genre> tmdbGenres = tmdbGenre.getGenreList("en");

			for (info.movito.themoviedbapi.model.Genre tmDBgenre : tmdbGenres)
			{
				if (this.genreRepository.findByName(tmDBgenre.getName()) == null)
				{
					Genre genre = new Genre(tmDBgenre.getId(), tmDBgenre.getName());

					this.persist(genre);
				}
			}
		}
		*/

		//MovieResultsPage result = tmdbSearch.searchMovie("Batman", null, "en-US", false, null);


		//System.out.println( result.toString());
		// TESTS ---------------------------------------------------------------------

	}
}
