package at.fh.ima.swengs.swengular.repository;

//import at.fh.ima.swengs.swengular.model.Genre;
import at.fh.ima.swengs.swengular.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long>
{
    //Get all users
    Set<User> findBy();

    User findById(@Param("id") Long id);

    User findByFirstName(@Param("firstName") String firstName);

    User findByLastName(@Param("lastName") String lastName);

    User findByLastNameAndFirstName(@Param("lastName") String lastName, @Param("firstName") String firstName);


    Set<User> findAllByLastName(@Param("lastName") String lastName);

    Set<User> findAllByFirstName(@Param("firstName") String firstName);

    Set<User> findAllByLastNameAndFirstName(@Param("lastName") String lastName, @Param("firstName") String firstName);


    Set<User> findAllByGenreIDsContaining(@Param("genreID") int genreID);

}
