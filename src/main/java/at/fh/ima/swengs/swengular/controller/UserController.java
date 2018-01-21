package at.fh.ima.swengs.swengular.controller;

import at.fh.ima.swengs.swengular.Exceptions.UserNotFoundException;
import at.fh.ima.swengs.swengular.model.User;
import at.fh.ima.swengs.swengular.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;



@CrossOrigin(origins = "*")
@RestController
public class UserController
{
    @Autowired
    UserRepository userRepository;



    // LOGIN
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/user/authenticate", method = RequestMethod.POST)
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<User> authenticate(@RequestBody User user)
    {
        User userToAuthenticate = userRepository.findByUsername(user.getUsername());

        if (userToAuthenticate != null)
        {
            if (user.getPassword().equals(userToAuthenticate.getPassword()))
            {
                return new ResponseEntity<User>(userToAuthenticate, HttpStatus.OK);
            }
        }

        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }



    // CREATE
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    // UPDATE
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userUpdate)
    {
        User user = userRepository.findById(id);

        if (user == null) { return new ResponseEntity<User>(HttpStatus.NOT_FOUND); }

        user.setUsername(userUpdate.getUsername());
        user.setPassword(userUpdate.getPassword());
        user.setGenreIDs(userUpdate.getGenreIDs());
        if (userUpdate.getFavouriteActorIDs() != null) user.setFavouriteActorIDs(userUpdate.getFavouriteActorIDs());
        if (userUpdate.getMovieLists() != null) user.setMovieLists(userUpdate.getMovieLists());
        if (userUpdate.getUsersFollowing() != null) user.setUsersFollowing(userUpdate.getUsersFollowing());

        userRepository.save(user);

        return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
    }



    // GET ALL USERS
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/users", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<Set<User>> getUsers()
    {
        Iterable<User> users = userRepository.findAll();
        if (users == null || !users.iterator().hasNext())
            return new ResponseEntity<Set<User>>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<Set<User>>(userRepository.findBy(), HttpStatus.OK);
    }



    // GET USER BY ID
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/user/{id}", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    User getUserByID(@PathVariable long id)
    {
        User user = userRepository.findById(id);

        if (user == null) throw new UserNotFoundException();

        return user;
    }



    // USER SEARCH
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/user/search", params = { "userName" }, produces = MediaType.APPLICATION_JSON_VALUE)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<Set<User>> searchUserByUserNameContaining(@RequestParam("userName") String userName)
    {
        Set<User> resultList = userRepository.findAllByUsernameContaining(userName);

        if (resultList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }



    // FOLLOW
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/user/follow", method = RequestMethod.POST)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<User> followUser(@RequestParam("userID") long userID,  @RequestParam long userToFollowID)
    {
        User activeUser = userRepository.findById(userID);

        User userToFollow = userRepository.findById(userToFollowID);

        activeUser.addUserFollowing(userToFollow);

        userRepository.save(activeUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    //------------------------------------------------------------------------------------------------------------------
    @ResponseStatus(value= HttpStatus.NOT_FOUND,reason="This user is not found in the system")
    //------------------------------------------------------------------------------------------------------------------



    //------------------------------------------------------------------------------------------------------------------
    @ExceptionHandler(UserNotFoundException.class)
    //------------------------------------------------------------------------------------------------------------------
    public void exceptionHandler() { }

}
