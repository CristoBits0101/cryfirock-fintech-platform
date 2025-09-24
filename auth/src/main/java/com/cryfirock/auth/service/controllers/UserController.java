package com.cryfirock.auth.service.controllers;

/**
 * Dependencies
 */
import com.cryfirock.auth.service.entities.User;
import com.cryfirock.auth.service.services.IUserService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// API controller allowing requests from all origins to /api/users
@RestController
@CrossOrigin(origins="http://localhost:8082", originPatterns = "*")
@RequestMapping("/api/users")
public class UserController {

    /**
     * Attributes
     */
    @Autowired
    private IUserService userService;

    /**
     * Allows you to create a new user
     * 
     * @param user the new user
     * @param result of the validation
     * @return ResponseEntity with validation errors or 201 and the created user
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        // Validates parameters that store the data of the sent JSON
        if (result.hasErrors())
            return validation(result);

        // Set admin to false by default for the created user
        user.setAdmin(false);

        // Saves the user to the database
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    /**
     * Allows you to create a new admin
     * 
     * @param user   the new admin
     * @param result of the validation
     * @return ResponseEntity with validation errors or 201 and the created user
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/superuser")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody User user, BindingResult result) {
        // Validates parameters that store the data of the sent JSON
        if (result.hasErrors())
            return validation(result);

        // Sets admin to true for the created user
        user.setAdmin(true);

        // Saves the user to the database
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    /**
     * Allows you to update a user
     * 
     * @param id   the id of the user
     * @param user the user to update
     * @return ResponseEntity with validation errors, 200 if updated or 404 if error
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result) {
        // Validates parameters that store the data of the sent JSON
        if (result.hasErrors())
            return validation(result);

        // Find the user to update
        Optional<User> userOptional = userService.findById(id);

        // Update the user if it already
        if (userOptional.isPresent()) {
            user.setId(id);
            return ResponseEntity.ok(userService.save(user));
        }

        // Returns error if the process failed
        return ResponseEntity.notFound().build();
    }

    /**
     * Retrieves all users
     * 
     * @return List of all users
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<User> getUsers() {
        // Search for users and return them in the response
        return userService.findAll();
    }

    /**
     * Retrieves a user by ID
     * 
     * @param id ID of the user
     * @return ResponseEntity with the user 200 or 404 if not found
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        // Find the user by ID
        Optional<User> userOptional = userService.findById(id);

        // Return the user if it exists, or error if not found
        if (userOptional.isPresent())
            return ResponseEntity.ok(userOptional.orElseThrow());

        // Returns error if the process failed
        return ResponseEntity.notFound().build();
    }

    /**
     * Allows you to delete a user
     * 
     * @param id ID of the user to delete
     * @return ResponseEntity with 200 if deleted, or 404 if not found
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // Create a new user object
        User user = new User();

        // Set the ID of the user to delete
        user.setId(id);

        // Find the user in the database
        Optional<User> userOptional = userService.deleteUser(user);

        // Return 200 if the user was deleted, or 404 if not found
        if (userOptional.isPresent())
            return ResponseEntity.ok(userOptional.orElseThrow());

        // Returns error if the process failed
        return ResponseEntity.notFound().build();
    }

    /**
     * Create the validation message
     * 
     * @param result contains the fields
     * @return the validation message
     */
    private ResponseEntity<?> validation(BindingResult result) {
        // Create a map to store the validation errors and their messages
        Map<String, String> errors = new HashMap<>();

        // Loop through each field with validation errors and add them to the map
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        // Return the validation errors with 400 status code
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}