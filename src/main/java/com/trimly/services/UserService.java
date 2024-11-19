package com.trimly.services;

import com.trimly.entities.Role;
import com.trimly.entities.User;
import com.trimly.repositories.RoleRepo;
import com.trimly.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user, String roleName) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));

        user.setRoles(Collections.singleton(role));
        return userRepository.save(user);
    }

    public  User findUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }


    // Method to authenticate user
    public User authenticate(String email, String password) {
        // Try to find the user by username
        User user = findUserByEmail(email);

        // Check if the user exists and if the password matches the encoded password
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user; // Return the full User object if authentication is successful
        }

        // Return null or throw an exception if the authentication fails
        return null;
    }
}
