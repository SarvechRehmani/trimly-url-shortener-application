package com.trimly.services;

import com.trimly.models.entities.Role;
import com.trimly.models.entities.User;
import com.trimly.repositories.RoleRepo;
import com.trimly.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public User registerUser(User user, String roleName) {

        Role role = roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        user.setRoles(roleList);
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }


    // Method to authenticate user
//    public User authenticate(String email, String password) {
//        // Try to find the user by username
//        User user = findUserByEmail(email);
//
//        // Check if the user exists and if the password matches the encoded password
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//            return user; // Return the full User object if authentication is successful
//        }
//
//        // Return null or throw an exception if the authentication fails
//        return null;
//    }
}
