package com.trimly.helper;

import com.trimly.exceptions.ResourceNotFoundException;
import com.trimly.models.entities.User;
import com.trimly.models.response.UserResponseDto;
import com.trimly.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUserHelper {

    public static User getAuthenticatedUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof User user) { // Replace `User` with your custom User class
            // Access user details
            System.out.println("User: " + user);

        } else {
            System.out.println("Principal is not an instance of User.");
            return null;
        }
        return user;
    }
}
