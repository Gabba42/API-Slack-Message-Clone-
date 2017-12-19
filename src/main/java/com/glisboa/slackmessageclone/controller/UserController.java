package com.glisboa.slackmessageclone.controller;


import com.glisboa.slackmessageclone.entity.Message;
import com.glisboa.slackmessageclone.entity.User;
import com.glisboa.slackmessageclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import org.apache.log4j.Logger;

@RestController
@RequestMapping("/users")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public Collection<User> getAllUsers() {
       return this.userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            logger.debug("User with id " + id + " doesn't exist");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
            logger.debug("User with id " + id + " is found");
            return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user == null) {
            logger.debug("User with " + id + "id is not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            logger.debug("User with " + id + "id is being deleted");
            userService.deleteUserById(id);
            return new ResponseEntity<Void>(HttpStatus.GONE);
        }

    }


}
