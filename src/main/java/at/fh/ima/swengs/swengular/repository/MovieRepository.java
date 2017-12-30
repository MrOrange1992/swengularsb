package at.fh.ima.swengs.swengular.repository;

//DEPRECATED
//Will use TMDBmovie instead
/*

import at.fh.ima.swengs.swengular.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long>
{
    Set<Movie> findByTitle(@Param("title") String title);

    //probably wont need this function
    Set<Movie> removeByTitle(@Param("title") String title);

}
*/