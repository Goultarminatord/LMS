package ma.cigma.lms.controllers;

import ma.cigma.lms.models.User;
import ma.cigma.lms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder builder){
        user = userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
    userService.deleteUser(id);
    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
