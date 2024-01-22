package com.api.chat.controller;

import com.api.chat.entity.User;
import com.api.chat.exception.UserExistsException;
import com.api.chat.payload.UserRequest;
import com.api.chat.payload.UserStatistics;
import com.api.chat.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{username}/statistics")
    public ResponseEntity<UserStatistics> getUserStatistics(@PathVariable(value = "username") String username) {
        UserStatistics userStatistics = userService.getStatistics(username);

        return new ResponseEntity<UserStatistics>(userStatistics, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserRequest user) {
        try{
            logger.info(user.toString());
            User newUser = userService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
        catch(Exception ex) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "username") String username) {
        try{
            userService.deleteByUsername(username);
            return new ResponseEntity<String>("User " + username + " deleted", HttpStatus.OK);
        }
        catch(UsernameNotFoundException ex) {
            return new ResponseEntity<String>("User with " + username + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
