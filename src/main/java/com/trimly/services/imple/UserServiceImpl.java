package com.trimly.services.imple;

import com.trimly.models.entities.User;
import com.trimly.repositories.UserRepo;
import com.trimly.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;


    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(User user, String roleName) {
        logger.info("Make list of String (role).");
        List<String> roles = new ArrayList<>(List.of(roleName));
        user.setRoles(roles);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        logger.info("Saving User into the Database.");
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

}
