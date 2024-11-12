package com.trimly.repositories;

import com.trimly.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
