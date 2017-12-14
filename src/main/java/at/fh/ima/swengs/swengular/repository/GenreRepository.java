package at.fh.ima.swengs.swengular.repository;

import at.fh.ima.swengs.swengular.model.Genre;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long>
{
    Set<Genre> findAll();

    Genre findByName(@Param("name") String name);

    void persist(Genre genre);


}
