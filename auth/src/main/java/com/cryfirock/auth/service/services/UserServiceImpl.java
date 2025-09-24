package com.cryfirock.auth.service.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cryfirock.auth.service.entities.Role;
import com.cryfirock.auth.service.entities.User;
import com.cryfirock.auth.service.repositories.RoleRepository;
import com.cryfirock.auth.service.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl {
    /**
     * Attributes
     */
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Saves a new user in the database
     * 
     * @param user User object containing details of the new user
     * @return The saved user
     */
    @Override
    @Transactional
    public User save(User user) {
        // Initialize a list to store user roles
        List<Role> roles = new ArrayList<>();

        // Assign the default "ROLE_USER" role
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");

        optionalRoleUser.ifPresent(roles::add);

        // If the user is an admin, add "ROLE_ADMIN"
        if (user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }

        // Set roles and encrypt the password before saving the user
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save and return the user
        return userRepository.save(user);
    }

    /**
     * Updates an existing user by ID
     * 
     * @param id   User ID
     * @param user User object containing updated details
     * @return Optional containing the updated user if found, otherwise empty
     */
    @Override
    @Transactional
    public Optional<User> update(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);

        // Throw an exception if user does not exist
        if (optionalUser.isEmpty())
            throw new UserNotFoundException("User " + id + " does not exist!");

        // Check if user exists before updating
        if (optionalUser.isPresent()) {
            User userToUpdate = optionalUser.get();

            // Update user fields
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPhoneNumber(user.getPhoneNumber());
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
            userToUpdate.setDob(user.getDob());
            userToUpdate.setAddress(user.getAddress());
            userToUpdate.setAccountStatus(user.getAccountStatus());

            // If the user is not an admin, ensure they only have the "ROLE_USER" role
            if (!user.isAdmin()) {
                List<Role> roles = new ArrayList<>();
                Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
                optionalRoleUser.ifPresent(roles::add);
                userToUpdate.setRoles(roles);
            }

            // Save and return the updated user
            return Optional.of(userRepository.save(userToUpdate));
        }
        return optionalUser;
    }

    /**
     * Retrieves all users from the database
     * 
     * @return List of all users
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Finds a user by their ID
     * 
     * @param id User ID
     * @return Optional containing the user if found, otherwise empty
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Checks if a user exists by email
     * 
     * @param email User email
     * @return True if the email exists, false otherwise
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Checks if a user exists by phone number
     * 
     * @param phoneNumber User phone number
     * @return True if the phone number exists, false otherwise
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    /**
     * Checks if a user exists by username
     * 
     * @param username User username
     * @return True if the username exists, false otherwise
     */
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Deletes a user from the database
     * 
     * @param user User object to be deleted
     * @return Optional containing the deleted user if found, otherwise empty
     */
    @Override
    @Transactional
    public Optional<User> deleteUser(User user) {
        // Check if user exists before deleting
        // Throw an exception if user does not exist
        Optional<User> userToDelete = Optional.ofNullable(
                userRepository
                        .findById(user.getId())
                        .orElseThrow(() -> new UserNotFoundException("User " + user.getId() + " does not exist!")));

        // If the user exists, delete them
        userToDelete.ifPresent(userRepository::delete);

        return userToDelete;
    }
}
