package com.trimly.services;

import com.trimly.models.entities.User;

import java.util.Optional;

public interface UserService {
    public User registerUser(User user, String roleName);
    public Optional<User> findUserByEmail(String email);

}
