package at.fh.ima.swengs.swengular.controller;

import at.fh.ima.swengs.swengular.Exceptions.UserNotFoundException;
import at.fh.ima.swengs.swengular.model.User;
import at.fh.ima.swengs.swengular.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController
{
    @Autowired
    UserRepository userRepository;


    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/user", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<Set<User>> getUsers()
    {
        Iterable<User> users = userRepository.findAll();
        if (users == null || !users.iterator().hasNext())
            return new ResponseEntity<Set<User>>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<Set<User>>(userRepository.findBy(), HttpStatus.OK);
    }



    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/user/{id}", method = RequestMethod.GET)
    //------------------------------------------------------------------------------------------------------------------
    User getUserByID(@PathVariable long id)
    {
        User user = userRepository.findById(id);

        if (user == null) throw new UserNotFoundException();

        return user;
    }


    /*          ONLY ADMIN
    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/user/{id}", method = RequestMethod.DELETE)
    //------------------------------------------------------------------------------------------------------------------
    ResponseEntity<User> deleteUser(@PathVariable long id) {
        User user = userRepository.findById(id);

        if (user == null) { return new ResponseEntity<User>(HttpStatus.NOT_FOUND); }

        userRepository.delete(user);

        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
    */


    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder)
    {
        userRepository.save(user);

        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());

        return new ResponseEntity<User>(headers, HttpStatus.CREATED);
    }



    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User userUpdate) {

        User user = userRepository.findById(id);

        if (user == null) { return new ResponseEntity<User>(HttpStatus.NOT_FOUND); }

        userRepository.save(userUpdate);

        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    //------------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/user/search", params = { "userName" }, produces = MediaType.APPLICATION_JSON_VALUE)
    //------------------------------------------------------------------------------------------------------------------
    public ResponseEntity<Set<User>> getUserList(@RequestParam("userName") String userName)
    {
        Set<User> resultList = userRepository.findAllByUserName(userName);

        if (resultList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


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
