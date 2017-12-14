package at.fh.ima.swengs.swengular.repository;

import at.fh.ima.swengs.swengular.model.Genre;
import at.fh.ima.swengs.swengular.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long>
{
    //find user by last/first name
    Set<User> findByLastName(@Param("lastName") String lastName);
    Set<User> findByFirstName(@Param("firstName") String firstName);
    Set<User> findByLastNameAndFirstName(@Param("lastName") String lastName, @Param("firstName") String firstName);

    Set<User> findByGenres(@Param("genre") Genre genre);    //TODO: don't know if this works

}
